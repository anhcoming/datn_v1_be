package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.discount.create.DiscountDto;
import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/discount")
@RequiredArgsConstructor
@Slf4j
public class AdminDiscountController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "tao moi ma KM")
    public Object create(@RequestBody DiscountDto payload) {
      return service.adminDiscountService.create(getCurrentUser(), payload);
    }

    @PostMapping("/search")
    @Operation(summary = "tim kiem")
    public ResponseEntity<Object> search(@RequestBody DiscountRequest payload) {
        log.info("start api /api/v1/admin/discount/search with payload: {}", JsonUtils.toJson(payload));
        return ResponseEntity.ok(service.adminDiscountDetailService.search(getCurrentUser(), payload));
    }

    @GetMapping("/detail")
    @Operation(summary = "chi tiet")
    public ResponseEntity<Object> detail(String id) {
        log.info("start api /api/v1/admin/discount/detail with payload: {}", id);
        return ResponseEntity.ok(service.adminDiscountDetailService.detail(getCurrentUser(), id));
    }

    @PostMapping("/delete")
    @Operation(summary = "xoa ma km")
    public ResponseEntity<Object> delete(String id) {
        log.info("start api /api/v1/admin/discount/delete with payload: {}", id);
        return ResponseEntity.ok(service.adminDiscountStatusService.delete(getCurrentUser(), id));
    }

    @PostMapping("/change-status")
    @Operation(summary = "API đổi trạng thái khuyến mãi")
    public ResponseEntity<ResData<String>> changeStatus(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.adminDiscountDetailService.changeStatus(getCurrentUser(), id));
    }
}
