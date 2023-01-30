package com.ws.masterserver.utils.validator.admin.category;

import com.ws.masterserver.dto.admin.category.CategoryDto;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.field.CategoryFields;
import com.ws.masterserver.utils.validator.ValidateUtils;

public class CategoryValidator {
    private CategoryValidator() {}

    public static void validCreate(CategoryDto dto) {
        ValidatorUtils.validNullOrEmpty(CategoryFields.NAME, dto.getName());
        ValidatorUtils.validLength(CategoryFields.NAME, dto.getName(), 4, 250);
        ValidatorUtils.validNullOrEmpty(CategoryFields.DES, dto.getDes());
        ValidatorUtils.validLength(CategoryFields.DES, dto.getDes(), 500, true);
        ValidatorUtils.validNullOrEmpty(CategoryFields.TYPE_ID, dto.getTypeId());
//        ValidatorUtils.validNullOrEmptyStringList(CategoryFields.PRODUCT_LIST, dto.getProductIds());
    }
}
