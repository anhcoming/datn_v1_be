package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.detail.DiscountDetailResponse;
import com.ws.masterserver.dto.admin.discount.no_page.CategoryDto;
import com.ws.masterserver.dto.admin.discount.no_page.CustomerDto;
import com.ws.masterserver.dto.admin.discount.no_page.CustomerTypeDto;
import com.ws.masterserver.dto.admin.discount.no_page.ProductDto;
import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.dto.admin.discount.search.DiscountResponse;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.AdminDiscountDetailService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.*;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDiscountDetailServiceImpl implements AdminDiscountDetailService {
    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, DiscountRequest payload) {
        log.info("AdminDiscountServiceImpl search() with payload: {}", JsonUtils.toJson(payload));
        AuthValidator.checkAdminAndStaff(currentUser);
        Pageable pageable = PageableUtils.getPageable(payload.getPageReq());
        if (StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            payload.setTextSearch("");
        }
        payload.setTextSearch("%" + payload.getTextSearch()
                .toUpperCase(Locale.ROOT)
                .trim() + "%");
        DiscountStatusEnums status = DiscountStatusEnums.from(payload.getStatus());
        String statusStr = status == null ? null : status.name();
        Page<DiscountEntity> discountEntityPage = repository.discountRepository
                .search(payload.getTextSearch(), statusStr, pageable);
        if (discountEntityPage.isEmpty()) {
            return PageData.setEmpty(payload.getPageReq());
        }
        return PageData.setResult(discountEntityPage.getContent()
                        .stream()
                        .map(o -> {
                            DiscountStatusEnums statusEnum = DiscountStatusEnums.from(o.getStatus());
                            Date startDate = o.getStartDate();
                            Date endDate = o.getEndDate();
                            Date createdDate = o.getCreatedDate();
                            long usageNumber = repository.discountRepository.getUsageNumberNow(o.getId());
                            return DiscountResponse.builder()
                                    .id(o.getId())
                                    .code(o.getCode())
                                    .des(DiscountUtils.getDes(o, repository))
                                    .status(statusEnum == null ? null : statusEnum.name())
                                    .statusName(statusEnum == null ? null : statusEnum.getName())
                                    .startDate(startDate)
                                    .startDateFmt(startDate == null ? null : DateUtils.toStr(startDate, DateUtils.F_DDMMYYYYHHMM))
                                    .endDate(endDate)
                                    .endDateFmt(endDate == null ? null : DateUtils.toStr(endDate, DateUtils.F_DDMMYYYYHHMM))
                                    .usageNumber(usageNumber)
                                    .usageLimit(o.getUsageLimit() == null ? null : o.getUsageLimit())
                                    .createdDate(createdDate)
                                    .createdDateFmt(createdDate == null ? null : DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYYHHMM))
                                    .statusClazz(this.getStatusClass(o.getStatus()))
                                    .canEdit(usageNumber == 0)
                                    .build();
                        })
                        .collect(Collectors.toList()),
                discountEntityPage.getNumber(),
                discountEntityPage.getSize(),
                discountEntityPage.getTotalElements());
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        DiscountEntity discount = repository.discountRepository.findByIdAndDeleted(id, false);
        if (discount == null) {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        }
        ApplyTypeEnums applyType = ApplyTypeEnums.from(discount.getApplyType());
        Object applyTypeValue = new ArrayList<>();
        switch (applyType) {
            case PRODUCT:
                applyTypeValue = repository.discountProductRepository.findByDiscountId(discount.getId())
                        .stream()
                        .map(o -> repository.productRepository.findByIdAndActive(o.getProductId(), true))
                        .map(o -> ProductDto.builder()
                                .id(o.getId())
                                .name(o.getName())
                                .build())
                        .collect(Collectors.toList());
                break;
            case CATEGORY:
                applyTypeValue = repository.discountCategoryRepository.findByDiscountId(discount.getId())
                        .stream()
                        .map(o -> repository.categoryRepository.findByIdAndActive(o.getCategoryId(), true))
                        .map(o -> CategoryDto.builder()
                                .id(o.getId())
                                .name(o.getName())
                                .productNumber(repository.productRepository.countByCategoryId(o.getId()))
                                .build())
                        .collect(Collectors.toList());
                break;
            case ALL_PRODUCT:
            default:
                break;
        }

        DiscountCustomerTypeEnums customerType = DiscountCustomerTypeEnums.from(discount.getCustomerType());
        Object customerSelectedList = new ArrayList<>();
        switch (customerType) {
            case ALL:
            case CUSTOMER:
                customerSelectedList = repository.discountCustomerRepository.findByDiscountId(discount.getId())
                        .stream()
                        .map(o -> repository.userRepository.findByIdAndActive(o.getUserId(), true))
                        .map(o -> CustomerDto.builder()
                                .id(o.getId())
                                .name((StringUtils.isNullOrEmpty(o.getFirstName()) ? "" : o.getFirstName()) + " " + (StringUtils.isNullOrEmpty(o.getLastName()) ? "" : o.getLastName()))
                                .email(o.getEmail())
                                .build())
                        .collect(Collectors.toList());
                break;
            case GROUP:
                customerSelectedList = repository.discountCustomerTypeRepository.findByDiscountId(discount.getId())
                        .stream()
                        .map(o -> repository.customerTypeRepository.findByIdAndActive(o.getCustomerTypeId(), true))
                        .map(o -> {
                            CustomerTypeEnums customerTypeEnum = CustomerTypeEnums.from(o.getName());
                            return CustomerTypeDto.builder()
                                    .id(o.getId())
                                    .name(customerTypeEnum == null ? null : customerTypeEnum.getName())
                                    .customerNumber(repository.customerGroupRepository.countByCustomerTypeId(o.getId()))
                                    .build();
                        })
                        .collect(Collectors.toList());
                break;
            default:
                break;
        }

        Date startDate = discount.getStartDate();
        Date endDate = discount.getEndDate();
        Long usageNumber = repository.discountRepository.getUsageNumberNow(discount.getId());
        DiscountStatusEnums statusEnum = DiscountStatusEnums.from(discount.getStatus());
        DiscountTypeEnums type = DiscountTypeEnums.from(discount.getType());
        DiscountPrerequisiteTypeEnums prerequisite = DiscountPrerequisiteTypeEnums.from(discount.getPrerequisiteType());

        long startDateMils = startDate.getTime();
        return DiscountDetailResponse.builder()
                .id(discount.getId())
                .code(discount.getCode())
                .des(DiscountUtils.getDes(discount, repository))
                .type(type == null ? null : type.name())
                .typeName(type == null ? null : type.getName())
                .typeValue(JsonUtils.fromJson(discount.getTypeValue(), Map.class))
                .applyType(applyType == null ? null : applyType.name())
                .applyTypeName(applyType == null ? null : applyType.getName())
                .applyTypeValue(applyTypeValue)
                .prerequisiteType(prerequisite == null ? null : prerequisite.name())
                .prerequisiteTypeName(prerequisite == null ? null : prerequisite.getName())
                .prerequisiteTypeValue(JsonUtils.fromJson(discount.getPrerequisiteValue(), Map.class))
                .customerType(customerType == null ? null : customerType.name())
                .customerTypeName(customerType == null ? null : customerType.getName())
                .customerTypeValue(customerSelectedList)
                .usageNumber(usageNumber)
                .usageLimit(discount.getUsageLimit())
                .oncePerCustomer(discount.getOncePerCustomer())
                .oncePerCustomerDetail(discount.getOncePerCustomer() ? "Giới hạn mỗi khách hàng chỉ được sử dụng mã giảm giá này 1 lần" : null)
                .startDate(startDate)
                .startDateFmt(DateUtils.toStr(startDate, DateUtils.F_DDMMYYYYHHMM))
                .startDateMils(startDateMils)
                .hasEndDate(endDate != null)
                .endDate(endDate)
                .endDateFmt(endDate == null ? null : DateUtils.toStr(startDate, DateUtils.F_DDMMYYYYHHMM))
                .endDateMils(endDate == null ? startDateMils : endDate.getTime())
                .status(statusEnum == null ? null : statusEnum.name())
                .statusName(statusEnum == null ? null : statusEnum.getName())
                .canEdit(usageNumber == null || 0L == usageNumber)
                .build();
    }

    private String getStatusClass(String status) {
        DiscountStatusEnums statusEnum = DiscountStatusEnums.from(status);
        if (statusEnum == null || DiscountStatusEnums.PENDING.equals(statusEnum)) {
            return null;
        }
        switch (statusEnum) {
            case ACTIVE:
                return "text-success";
            case PENDING:
                return "text-primary";
            case DE_ACTIVE:
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public ResData<String> changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null) {
            throw new WsException(WsCode.DISCOUNT_NOT_FOUND);
        }
        DiscountEntity discount = repository.discountRepository.findById(id).orElse(null);
        discount.setActive(!discount.getActive());
        repository.discountRepository.save(discount);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(discount));
        return new ResData<>(discount.getId(), WsCode.OK);
    }

    private String getUsageNumber(Long usageLimit, Long usedNumber) {
        if (usageLimit == null) {
            return usedNumber.toString();
        }
        return usedNumber + "/" + usageLimit;
    }
}
