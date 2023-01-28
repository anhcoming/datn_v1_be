package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.discount.no_page.CategoryDto;
import com.ws.masterserver.service.CategoryInfoService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryInfoServiceImpl implements CategoryInfoService {
    private final WsRepository repository;

    @Override
    public Object noPage(CurrentUser currentUser) {
        AuthValidator.checkLogin(currentUser);
        return ResData.ok(repository.categoryRepository.noPage().stream().map(o -> CategoryDto.builder()
                .id(o.getId())
                .name(o.getName())
                .productNumber(repository.productRepository.countByCategoryId(o.getId()))
                .build()).collect(Collectors.toList()));
    }

    @Override
    public Object noPage() {
        return repository.categoryRepository.noPage().stream().map(o -> CategoryDto.builder()
                .id(o.getId())
                .name(o.getName())
                .slug(o.getSlug())
                .productNumber(repository.productRepository.countByCategoryId(o.getId()))
                .build()).collect(Collectors.toList());


    }
}
