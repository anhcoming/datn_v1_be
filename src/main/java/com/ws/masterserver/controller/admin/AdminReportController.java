package com.ws.masterserver.controller.admin;

import com.ws.masterserver.dto.admin.report.customer.CustomerReportReq;
import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueReq;
import com.ws.masterserver.dto.admin.report.overview.ReportOverviewReq;
import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.utils.base.WsController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/admin/report")
@RequiredArgsConstructor
@Slf4j
public class AdminReportController extends WsController {

    @PostMapping("/overview")
    public Object overview(@RequestBody ReportOverviewReq payload) {
        return service.adminReportOverviewService.overview(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/product")
    @Operation(summary = "doanh thu theo san pham")
    public Object productRevenue(@RequestBody ProductRevenueReq payload) {
        return service.adminProductRevenueService.get(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/product/export")
    @Operation(summary = "doanh thu theo san pham")
    public Object exportProductRevenue(@RequestBody ProductRevenueReq payload) {
        return service.adminProductRevenueService.export(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/customer")
    public Object customerRevenue(@RequestBody CustomerReportReq payload) {
        log.info("start api POST: /api/v1/admin/report/revenue/customer with payload: {}", payload);
        return service.customerReportService.get(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/customer/export")
    @Operation(summary = "doanh thu theo khach hang")
    public Object exportCustomerRevenue(@RequestBody CustomerReportReq payload) {
        return service.customerReportService.export(getCurrentUser(), payload);
    }

    @GetMapping("/time-type")
    public Object getTimeTypes() {
        return service.adminReportTimeTypeService.getAll(getCurrentUser());
    }

    @PostMapping("/revenue-detail")
    public Object revenueDetail(@RequestBody ReportOverviewReq payload) {
        return service.adminRevenueDetailReportService.detail(getCurrentUser(), payload);
    }

    @PostMapping("/revenue-detail/export")
    public Object revenueDetailExport(@RequestBody ReportOverviewReq payload) {
        return service.adminRevenueDetailReportService.export(getCurrentUser(), payload);
    }

    @PostMapping("/user-detail")
    public Object reportUserDetail(@RequestBody ReportOverviewReq payload) {
        log.info("start api POST: /api/v1/admin/report/user-detail with payload: {}", payload);
        return service.adminUserDetailReportService.report(getCurrentUser(), payload);
    }

    @PostMapping("/user-detail/export")
    public Object exportReportUserDetail(@RequestBody ReportOverviewReq payload) {
        log.info("start api POST: /api/v1/admin/report/user-detail/export with payload: {}", payload);
        return service.adminUserDetailReportService.export(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/discount")
    public Object discountRevenueReport(@RequestBody DiscountRevenueReq payload) {
        return service.adminDiscountRevenueReportService.get(getCurrentUser(), payload);
    }

    @PostMapping("/revenue/discount/export")
    public Object discountRevenueReportExport(@RequestBody DiscountRevenueReq payload) {
        return service.adminDiscountRevenueReportService.export(getCurrentUser(), payload);
    }

    @PostMapping("/export/template")
    public Object exportTemplate() throws IOException {
        // Creating an object of File class and
        // providing local directory path of a file
        File path = new File(
                "C:\\FPT_Polytechnic\\DATN_DA\\datn_v1_fe_client\\src\\assets\\img\\slider\\template_product.csv");

        // Calling the Method1 in main() to
        // convert file to byte array
        byte[] array = method(path);

        // Printing the byte array
        System.out.print(Arrays.toString(array));
        return array;
    }

    public static byte[] method(File file)
            throws IOException {

        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);

        // Now creating byte array of same length as file
        byte[] arr = new byte[(int) file.length()];

        // Reading file content to byte array
        // using standard read() method
        fl.read(arr);

        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();

        // Returning above byte array
        return arr;
    }
}
