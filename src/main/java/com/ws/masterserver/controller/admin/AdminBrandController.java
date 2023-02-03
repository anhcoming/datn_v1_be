package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.enum_dto.BrandDto;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/brand")
@RequiredArgsConstructor
@Slf4j
public class AdminBrandController extends WsController {

    @PostMapping("/create")
    @Operation(summary = "API thêm mới thương hiệu sp")
    public ResponseEntity<ResData<String>> create(@RequestBody BrandDto dto){
        log.info("start api create with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.brandService.create(getCurrentUser(), dto));
    }

    @PostMapping("/update")
    @Operation(summary = "API sửa thương hiệu sp")
    public ResponseEntity<ResData<String>> update(@RequestBody BrandDto dto){
        log.info("start api update with dto: {}", JsonUtils.toJson(dto));
        return ResponseEntity.status(HttpStatus.OK).body(service.brandService.update(getCurrentUser(), dto));
    }

    @PostMapping("/delete")
    @Operation(summary = "API xóa thương hiệu sp")
    public ResponseEntity<ResData<String>> delete(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.brandService.delete(getCurrentUser(), id));
    }

    @PostMapping("/change-status")
    @Operation(summary = "API đổi trạng thái brand")
    public ResponseEntity<ResData<String>> changeStatus(String id){
        log.info("start api delete with dto: {}", JsonUtils.toJson(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.brandService.changeStatus(getCurrentUser(), id));
    }

    @GetMapping("/detail")
    @Operation(summary = "API admin chi tiết brand")
    public Object detail(String id) {
        log.info("start api detail with dto: {}", id);
        return service.brandService.detail(getCurrentUser(), id);
    }
    @Operation(summary = "API lấy tất cả Brand")
    @PostMapping("/search")
    public Object search(@RequestBody SizeReq payload) {
        return service.brandService.search(getCurrentUser(), payload);
    }
}
