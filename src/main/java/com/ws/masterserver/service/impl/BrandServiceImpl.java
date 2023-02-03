package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.brand.BrandRes;
import com.ws.masterserver.dto.admin.size.SizeResponseV2;
import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.dto.admin.size.search.SizeRes;
import com.ws.masterserver.entity.BrandEntity;
import com.ws.masterserver.entity.ColorEntity;
import com.ws.masterserver.entity.SizeEntity;
import com.ws.masterserver.service.BrandService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.BrandDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StatusResUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.stream.Collectors;

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
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null || Boolean.FALSE.equals(repository.brandRepository.findByIdAndActive(id, Boolean.TRUE))) {
            throw new WsException(WsCode.BRAND_NOT_FOUND);
        }
        BrandEntity brand = repository.brandRepository.findById(id)
                .orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        repository.brandRepository.delete(brand);
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

    @Override
    @Transactional
    public ResData<String> changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        BrandEntity brand = repository.brandRepository.findById(id).orElse(null);
        brand.setActive(!brand.getActive());
        repository.brandRepository.save(brand);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(brand));
        return new ResData<>(brand.getId(), WsCode.OK);
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        BrandEntity brand = repository.brandRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        return SizeResponseV2.builder()
                .id(brand.getId())
                .name(brand.getName())
                .active(brand.getActive())
                .build();
    }
    
    @Override
    public Object search(CurrentUser currentUser, SizeReq payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        log.info("search() payload: {}", JsonUtils.toJson(payload));
        PageReq pageReq = payload.getPageReq();
        PageableUtils.getPageReq(pageReq);
        String textSearch = payload.getTextSearch();
        if(StringUtils.isNullOrEmpty(textSearch)) {
            textSearch = "";
        }
        textSearch = textSearch.toUpperCase();
        Pageable pageable = PageableUtils.getPageable(pageReq);
        Page<BrandEntity> branPage = repository.brandRepository.search(textSearch, payload.getActive(), pageable);
        return PageData.setResult(
        		branPage.getContent().stream().map(BrandServiceImpl::convertEntity2Res).collect(Collectors.toList()),
        		branPage.getNumber(),
        		branPage.getSize(),
        		branPage.getTotalElements());
    }

    private static BrandRes convertEntity2Res(BrandEntity size) {
        return BrandRes.builder()
                .id(size.getId())
                .name(size.getName())
                .status(StatusResUtils.getStatus(size.getActive()))
                .build();
    }
}
