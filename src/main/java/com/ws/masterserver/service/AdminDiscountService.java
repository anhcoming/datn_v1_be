package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

/**
 * @author myname
 */
public interface AdminDiscountService {
    Object create(CurrentUser currentUser, DiscountDto payload);

    ResData<String> changeStatus(CurrentUser currentUser, String id);

}
