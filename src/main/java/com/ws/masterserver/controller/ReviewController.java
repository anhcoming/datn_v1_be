package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.review.request.CheckReview;
import com.ws.masterserver.dto.customer.review.request.ReviewFilter;
import com.ws.masterserver.dto.customer.review.request.ReviewRequest;
import com.ws.masterserver.dto.customer.review.request.ReviewUpdateReq;
import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController extends WsController {

    @PostMapping("/search")
    public ResponseEntity<?> createReview(@RequestBody ReviewFilter req){
        log.info("----- API /api/v1/review/search --------");
        return ResponseEntity.ok(service.reviewService.listReview(req));
    }

    @Operation(summary = "API create review")
    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest req){
        log.info("----- API /api/v1/review/CREATE --------");
        return ResponseEntity.ok(service.reviewService.createReview(getCurrentUser(),req));
    }

    @Operation(summary = "API update review")
    @PostMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateReq req){
        log.info("----- API /api/v1/review/update --------");
        return ResponseEntity.ok(service.reviewService.updateReview(getCurrentUser(),req));
    }

    @Operation(summary = "API DELETE review")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") String id){
        log.info("----- API /api/v1/review/delete --------");
        service.reviewService.deleteReview(getCurrentUser(),id);
        return ResponseEntity.ok("Delete review successfully !!");

    }

    @PostMapping("/checkReview")
    public ResponseEntity<?> checkReview(@RequestBody CheckReview req){
        log.info("----- API /api/v1/review/check --------");
        return ResponseEntity.ok(service.reviewService.checkExistReviewProduct(getCurrentUser(),req));
    }

    @GetMapping("/checkReview/{id}")
    public ResponseEntity<?> checkReview2(@PathVariable("id") String id){
        log.info("----- API /api/v1/review/checkReview/id --------");
        return ResponseEntity.ok(service.reviewService.checkExistProductReview(getCurrentUser(),id));
    }


    @GetMapping("/checkReviewV2/{orderId}/{productId}")
    public ResponseEntity<?> checkReviewV2(@PathVariable("orderId") String orderId,@PathVariable("productId") String productId){
        log.info("----- API /api/v1/review/checkReview/id --------");
        return ResponseEntity.ok(service.reviewService.checkExistProductReviewV2(orderId,productId));
    }

}
