package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.order.change_status.ChangeStatusDto;
import com.ws.masterserver.dto.dob.DiscountNotificationDto;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.job.DiscountNotificationJob;
import com.ws.masterserver.service.AdminDiscountService;
import com.ws.masterserver.service.MailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.admin.discount.AdminDiscountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDiscountServiceImpl implements AdminDiscountService {
    private final WsRepository repository;
    private final MailService mailService;
    private static final String SPACE = " ";

    @Override
    @Transactional
    public Object create(CurrentUser currentUser, DiscountDto payload) {
        log.info("DiscountServiceImpl create() with payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkAdminAndStaff(currentUser);
        AdminDiscountValidator.validCreateDtoData(payload);
        AdminDiscountValidator.validCreateDtoConstraint(payload, repository);

        DiscountEntity discount = new DiscountEntity();
        String discountId = UidUtils.generateUid();
        discount.setId(discountId);
        discount.setCode(payload.getCode().trim().replaceAll(" ", "").toUpperCase());

        /**
         * loại km
         * */
        DiscountTypeEnums type = DiscountTypeEnums.valueOf(payload.getType());
        discount.setType(type.name());
        this.saveTypeValue(payload, discount);

        /**
         * ap dung cho
         * */
        //5. applyType
        ApplyTypeEnums applyType = ApplyTypeEnums.valueOf(payload.getApplyType());
        discount.setApplyType(applyType.name());

        /**
         * Điều kiện áp dụng
         * */
        DiscountPrerequisiteTypeEnums prerequisiteType = DiscountPrerequisiteTypeEnums.valueOf(payload.getPrerequisiteType());
        discount.setPrerequisiteType(prerequisiteType.name());
        this.savePrerequisiteValue(payload, discount, prerequisiteType);

        /**
         * Chỉ lưu dsasch sản phẩm đượcc áp dụng và điều kiện áp dụng nếu mã KM k là loại vận chuyển
         * */
        if (!DiscountTypeEnums.SHIP.equals(type)) {
            this.saveApplyTypeValue(payload, discountId, applyType);
            this.savePrerequisiteValue(payload, discount, prerequisiteType);
        }

        /**
         * Nhóm khách hàng
         * */
        DiscountCustomerTypeEnums customerType = DiscountCustomerTypeEnums.valueOf(payload.getCustomerType());
        discount.setCustomerType(customerType.name());
        this.saveDiscountCustomerType(payload, discountId, customerType);

        /**
         * giới hạn số lần mã giảm giá được áp dụng
         */
        if (!StringUtils.isNullOrEmpty(payload.getUsageLimit())) {
            discount.setUsageLimit(Long.valueOf(payload.getUsageLimit()));
        }

        /**
         * Giói hạn mỗi khách hàng chỉ được sử dụng mã này 1 lần hay không(Kiểm tra bằng email) ?
         */
        if (payload.getOncePerCustomer() == null) {
            payload.setOncePerCustomer(false);
        }
        discount.setOncePerCustomer(payload.getOncePerCustomer());

        /**
         * mặc định trạng thái = chưa diễn ra
         * */
        DiscountStatusEnums status = DiscountStatusEnums.PENDING;

        /**
         * Thời gian
         */
        Date start = DateUtils.toDate(payload.getStartDate(), DateUtils.F_DDMMYYYYHHMM);
        discount.setStartDate(start);

        /**
         * Nếu ngày bắt đầu < hien tai => đang diễn ra
         * */
        if (start.before(new Date())) {
            status = DiscountStatusEnums.ACTIVE;
        }

        if (payload.getHasEndDate()) {
            Date end = DateUtils.toDate(payload.getEndDate(), DateUtils.F_DDMMYYYYHHMM);
            discount.setEndDate(end);
            /**
             * Nếu ngày kết thúc < hiện tại => đã hết hạn
             * */
            if (end.before(new Date())) {
                status = DiscountStatusEnums.DE_ACTIVE;
            }
        }
        discount.setStatus(status.name());
        discount.setDeleted(false);
        discount.setActive(true);
        log.info("create() discount before save: {}", JsonUtils.toJson(discount));
        repository.discountRepository.save(discount);
        log.info("create() discount after save: {}", JsonUtils.toJson(discount));

        this.sendMail(discount);

        return discount.getId();
    }

    private void sendMail(DiscountEntity discount) {
        DiscountCustomerTypeEnums customerType = DiscountCustomerTypeEnums.from(discount.getCustomerType());
        Set<String> emails = new HashSet<>();
        switch (customerType) {
            case GROUP:
                List<DiscountCustomerTypeEntity> discountCustomers = repository.discountCustomerTypeRepository.findByDiscountId(discount.getId());
                if (discountCustomers.isEmpty()) {
                    break;
                }
                for (DiscountCustomerTypeEntity o : discountCustomers) {
                    List<CustomerGroupEntity> customerGroups = repository.customerGroupRepository.findByCustomerTypeId(o.getId());
                    if (customerGroups.isEmpty()) {
                        break;
                    }
                    for (CustomerGroupEntity o1 : customerGroups) {
                        UserEntity customer = repository.userRepository.findByIdAndActive(o1.getUserId(), true);
                        if (customer != null && !StringUtils.isNullOrEmpty(customer.getEmail())) {
                            emails.add(customer.getEmail());
                        }
                    }
                }
                break;
            case ALL:
            case CUSTOMER:
                repository.discountCustomerRepository.findByDiscountId(discount.getId())
                        .stream().map(DiscountCustomerEntity::getUserId)
                        .forEach(item -> {
                            UserEntity customer = repository.userRepository.findByIdAndActive(item, true);
                            if (customer != null && !StringUtils.isNullOrEmpty(customer.getEmail())) {
                                emails.add(customer.getEmail());
                            }
                        });
                break;
            default:
                break;
        }
        if (!emails.isEmpty()) {
            String discountId = discount.getId();
            for (String email : emails) {
                DiscountNotificationJob.add(DiscountNotificationDto.builder()
                        .discountId(discountId)
                        .email(email)
                        .build());
            }
        }
    }

    private void saveTypeValue(DiscountDto payload, DiscountEntity discount) {
        Object typeValues = payload.getTypeValue();
        discount.setTypeValue(JsonUtils.toJson(typeValues));
    }

    private void savePrerequisiteValue(DiscountDto payload, DiscountEntity discount, DiscountPrerequisiteTypeEnums prerequisiteType) {
        if (!DiscountPrerequisiteTypeEnums.NONE.equals(prerequisiteType)) {
            discount.setPrerequisiteValue(JsonUtils.toJson(payload.getPrerequisiteTypeValue()));
        }
    }

    private void saveDiscountCustomerType(DiscountDto payload, String discountId, DiscountCustomerTypeEnums customerType) {
        switch (customerType) {
            case GROUP:
                payload.getCustomerIds().forEach(o -> {
                    repository.discountCustomerTypeRepository.save(DiscountCustomerTypeEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .customerTypeId(o)
                            .build());
                    repository.customerGroupRepository.findByCustomerTypeId(o)
                            .stream()
                            .map(CustomerGroupEntity::getUserId)
                            .distinct()
                            .forEach(customerId -> repository.discountCustomerRepository
                                    .save(DiscountCustomerEntity.builder()
                                            .id(UidUtils.generateUid())
                                            .discountId(discountId)
                                            .userId(customerId)
                                            .build()));
                });
                break;
            case CUSTOMER:
                payload.getCustomerIds().forEach(o ->
                        repository.discountCustomerRepository.save(DiscountCustomerEntity.builder()
                                .id(UidUtils.generateUid())
                                .discountId(discountId)
                                .userId(o)
                                .build()));
                break;
            case ALL:
                List<String> customerIds = repository.userRepository.findUserIdByRole(RoleEnum.ROLE_CUSTOMER);
                customerIds.forEach(o -> repository.discountCustomerRepository
                        .save(DiscountCustomerEntity.builder()
                                .id(UidUtils.generateUid())
                                .discountId(discountId)
                                .userId(o)
                                .build()));
                break;
            default:
                break;
        }
    }

    private void saveApplyTypeValue(DiscountDto payload, String discountId, ApplyTypeEnums applyType) {
        switch (applyType) {
            case CATEGORY:
                payload.getApplyTypeIds().forEach(o -> {
                    repository.discountCategoryRepository.save(DiscountCategoryEntity.builder()
                            .id(UidUtils.generateUid())
                            .discountId(discountId)
                            .categoryId(o)
                            .build());
                    repository.productRepository.findByCategoryIdAndActive(o, true)
                            .forEach(product -> repository.discountProductRepository.save(DiscountProductEntity.builder()
                                    .id(UidUtils.generateUid())
                                    .discountId(discountId)
                                    .productId(product.getId())
                                    .build()));
                });
                break;
            case ALL_PRODUCT:
                repository.productRepository.findAllByActive(Boolean.TRUE).stream()
                        .map(ProductEntity::getId).forEach(o -> repository.discountProductRepository.save(DiscountProductEntity.builder()
                                .id(UidUtils.generateUid())
                                .discountId(discountId)
                                .productId(o)
                                .build()));
                break;
            case PRODUCT:
                payload.getApplyTypeIds().forEach(o ->
                        repository.discountProductRepository.save(DiscountProductEntity.builder()
                                .id(UidUtils.generateUid())
                                .discountId(discountId)
                                .productId(o)
                                .build()));
                break;
            default:
                break;
        }
    }

    @Override
    @Transactional
    public Object changeStatus(CurrentUser currentUser, ChangeStatusDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (StringUtils.isNullOrEmpty(dto.getStatus())) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        DiscountEntity disount = repository.discountRepository.findByIdLock(dto.getId());
        DiscountStatusEnums status = DiscountStatusEnums.from(dto.getStatus().toUpperCase(Locale.ROOT));
        DiscountStatusEnums nowStatus = DiscountStatusEnums.from(disount.getStatus());
        if (status == null || disount == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }

        String stt = dto.getStatus();
        if (stt == "ACTIVE") {
            if (DiscountStatusEnums.ACTIVE.equals(nowStatus)) {
                throw new WsException(WsCode.DISCOUNT_PENDINGACTIVE);
            }
        } else if (stt == "DE_ACTIVE"){
            if (DiscountStatusEnums.DE_ACTIVE.equals(nowStatus)) {
                throw new WsException(WsCode.DISCOUNT_PENDINGDE_ACTIVE);
            }
        }

        disount.setStatus(status.name());
        disount.setUpdatedBy(currentUser.getId());
        disount.setUpdatedDate(new Date());
        repository.discountRepository.save(disount);

        return disount.getStatus();
    }
}
