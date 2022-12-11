package com.ws.masterserver.service.impl;

import com.ws.masterserver.entity.BrandEntity;
import com.ws.masterserver.service.BrandService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.BrandDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, BrandDto dto) {
        //dasdas
        AuthValidator.checkAdminAndStaff(currentUser);
        java.util.Optional<com.ws.masterserver.entity.ProductEntity> products = repository.productRepository.findById(dto.getId());

        if (Boolean.FALSE.equals(products != null)){

            throw new WsException(WsCode.PRODUCT_NOT_FOUND);
        }
        BrandEntity brand = BrandEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .active(Boolean.TRUE)
                .build();
        repository.brandRepository.save(brand);
        log.info("creat finished at {} with response: {}", new Date(), JsonUtils.toJson(brand));
        return new ResData<>(brand.getId(), WsCode.OK);
    }

    @Override
    public ResData<String> delete(CurrentUser currentUser, BrandDto dto) {
        AuthValidator.checkAdmin(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.BRAND_NOT_FOUND);
        }
        BrandEntity brand = repository.brandRepository.findByIdAndActive(dto.getId(),Boolean.TRUE);
        brand.setActive(Boolean.FALSE);
        repository.brandRepository.save(brand);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(brand));
        return new ResData<>(brand.getId(), WsCode.OK);
    }

    @Override
    public ResData<String> update(CurrentUser currentUser, BrandDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.brandRepository.existsByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.BRAND_NOT_FOUND);
        }
        BrandEntity brand = repository.brandRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        brand.setName(dto.getName().trim());
        repository.brandRepository.save(brand);
        log.info("update finish at {} with response: {}" ,new Date(), JsonUtils.toJson(brand));
        return new ResData<>(brand.getId(), WsCode.OK);
    }

    @Override
    public Object noPage() {
        return repository.brandRepository.findByActive(true);
    }
}
