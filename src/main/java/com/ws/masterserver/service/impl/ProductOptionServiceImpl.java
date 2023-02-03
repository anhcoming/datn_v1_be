package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdRes;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.entity.ColorEntity;
import com.ws.masterserver.entity.ProductOptionEntity;
import com.ws.masterserver.service.ProductOptionService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOptionServiceImpl implements ProductOptionService {

    private final WsRepository repository;

    @Override
    public ResData<ProductOptionIdRes> findProductOptionId(ProductOptionIdReq req) {
        ProductOptionEntity productOption = repository.productOptionRepository.findBySizeAndColorId(req.getSizeId(),req.getColorId(),req.getProductId());
        if(productOption == null) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        ProductOptionIdRes res = ProductOptionIdRes.builder()
                .productOptionId(productOption.getId())
                .quantity(productOption.getQty())
                .price(productOption.getPrice())
                .build();
        return new ResData<>(res, WsCode.OK);
    }


//    @Override
//    public ResData<List<ColorResponse>> findColorNameBySize(String sizeId,String productId) {
//        List<ColorResponse> color = repository.productOptionRepository.getListColorNameBySize(sizeId,productId);
//        log.info("----------------" + color);
//        return new ResData<>(color, WsCode.OK);
//    }
@Override
public ResData<List<ColorResponse>> findColorNameBySize(String productId,String sizeId) {
    List<ColorResponse> color = repository.productOptionRepository.getListColorNameBySize(productId,sizeId);
    log.info("----------------" + color);
    return new ResData<>(color, WsCode.OK);
}

    @Override
    public ResData<List<SizeResponse>> findSizeByProductId(String productId) {
        List<SizeResponse> size = repository.productOptionRepository.findListSizeByProductId(productId);
        return new ResData<>(size, WsCode.OK);
    }

    @Override
    public ResData<ColorResponse> findCoLorByHex(String hex) {
        ColorResponse res = repository.productOptionRepository.findByHexAndActive(hex, true);
        return new ResData<>(res, WsCode.OK);
    }

    @Override
    public ResData<SizeResponse> findSizeByName(String sizeName) {
        SizeResponse res = repository.productOptionRepository.findSizeByName(sizeName, true);
        return new ResData<>(res, WsCode.OK);
    }
}
