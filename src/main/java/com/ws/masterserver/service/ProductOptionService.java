package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdReq;
import com.ws.masterserver.dto.customer.product.product_option.ProductOptionIdRes;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.constants.enums.SizeEnum;

import java.util.List;

public interface ProductOptionService {
    ResData<ProductOptionIdRes> findProductOptionId(ProductOptionIdReq req);
    ResData<List<ColorResponse>> findColorNameBySize(String sizeId,String productId);
    ResData<List<SizeResponse>> findSizeByProductId(String productId);

    ResData<ColorResponse> findCoLorByHex(String hex);

    ResData<SizeResponse> findSizeByName(String sizeName);
}
