package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.dto.customer.order.order_detail.OrderDetailResponse;
import com.ws.masterserver.dto.customer.review.request.CheckReview;
import com.ws.masterserver.dto.customer.review.request.ReviewFilter;
import com.ws.masterserver.dto.customer.review.request.ReviewRequest;
import com.ws.masterserver.dto.customer.review.request.ReviewUpdateReq;
import com.ws.masterserver.dto.customer.review.response.ReviewResponse;
import com.ws.masterserver.entity.ReviewEntity;
import com.ws.masterserver.service.ReviewService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.DateUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final WsRepository repository;


    @Override
    public Object listReview(ReviewFilter req) {

        log.info("--- list review search ----");
        Pageable pageable = PageableUtils.getPageable(req.getPageReq());

        Page<ReviewEntity> reviewList = null;

        if(req.getRate() == null){
            reviewList = repository.reviewRepository.search(req.getProductId(),pageable);
        }else{
            reviewList = repository.reviewRepository.search(req.getProductId(), req.getRate(), pageable);
        }

        if(reviewList.isEmpty()){
            return PageData.setEmpty(req.getPageReq());
        }

        return PageData.setResult(
                reviewList.getContent()
                        .stream()
                        .map(review -> {
                            ReviewResponse reviewResponse = new ReviewResponse();
                            String userName = repository.reviewRepository.getUserNameReview(review.getUserId(),review.getProductId());

                            reviewResponse.setReviewId(review.getId());
                            reviewResponse.setUserName(userName);
                            reviewResponse.setActive(review.getActive());
                            reviewResponse.setContent(review.getContent());
                            reviewResponse.setRating(review.getRating());
                            reviewResponse.setCreatedDate(DateUtils.parseDateToStr(DateUtils.DATE_TIME_FORMAT_VI,review.getCreatedDate()));

                            return reviewResponse;
                        }).collect(Collectors.toList()),
                reviewList.getNumber(),
                reviewList.getSize(),
                reviewList.getTotalElements()
        );

    }

    @Override
    public Object createReview(CurrentUser currentUser,ReviewRequest request) {
        //check xem user đã mua hàng chưa
        List<OrderDetailResponse> orderDetail = repository.orderDetailRepository.checkConditionReview(currentUser.getId(),request.getProductId(),request.getOrderId());

        if(orderDetail == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng! ");
        }


        ReviewEntity review = ReviewEntity.builder()
                .id(UidUtils.generateUid())
                .createdBy(currentUser.getCombinationName())
                .createdDate(new Date())
                .active(Boolean.TRUE)
                .rating(request.getRating())
                .reviewId("ok")
                .productId(request.getProductId())
                .content(request.getContent())
                .userId(currentUser.getId())
                .orderId(request.getOrderId())
                .build();

        repository.reviewRepository.save(review);

        return new ResData<>(review.getId(),WsCode.CREATED);
    }

    @Override
    public void deleteReview(CurrentUser currentUser, String reviewId) {
        ReviewEntity review = repository.reviewRepository.findById(reviewId).orElseThrow(() -> {
            throw new WsException(WsCode.REVIEW_NOT_FOUND);
        });


        if(!review.getUserId().equals(currentUser.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Không có quyền xóa đánh giá này !");
        }

        repository.reviewRepository.deleteById(reviewId);
    }

    @Override
    public Object updateReview(CurrentUser currentUser, ReviewUpdateReq rq) {
        ReviewEntity review = repository.reviewRepository.findById(rq.getId()).orElseThrow(() -> {
            throw new WsException(WsCode.REVIEW_NOT_FOUND);
        });

        if(!review.getUserId().equals(currentUser.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Không có chỉnh sửa đánh giá này !");
        }

        review.setUpdatedBy(currentUser.getCombinationName());
        review.setUpdatedDate(new Date());
        review.setContent(rq.getContent());
        review.setRating(rq.getRating());

        repository.reviewRepository.save(review);

        return new ResData<>(review.getId(),WsCode.UPDATED);
    }

    @Override
    public Object checkExistReviewProduct(CurrentUser currentUser, CheckReview req) {

        boolean check = repository.reviewRepository.existsByUserIdAndProductId(currentUser.getId(),req.getProductId());
        if(check == true){
            return new ResData<>(true,WsCode.OK);
        }else{
            return new ResData<>(false,WsCode.OK);
        }
    }

    @Override
    public Object checkExistProductReview(CurrentUser currentUser, String id) {
        boolean check = repository.reviewRepository.checkExistReview(currentUser.getId(),id);
        return new ResData<>(check,WsCode.OK);
    }

//    @Override
//    public ResData<List<BlogResponse>> getAllBlog() {
//        List<BlogResponse> blog = repository.blogRepository.getAllBlog();
//        return new ResData<>(blog, WsCode.OK);
//    }
    @Override
    public ResData<List<?>> checkExistProductReviewV2(String orderId,String productId) {
        List<?> review = repository.reviewRepository.checkExistReviewV2(orderId,productId);
        return new ResData<>(review, WsCode.OK);
        };

}
