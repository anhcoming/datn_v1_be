package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.review.request.CheckReview;
import com.ws.masterserver.dto.customer.review.request.ReviewFilter;
import com.ws.masterserver.dto.customer.review.request.ReviewRequest;
import com.ws.masterserver.dto.customer.review.request.ReviewUpdateReq;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface ReviewService {

    Object listReview(ReviewFilter req);

    Object createReview(CurrentUser currentUser,ReviewRequest request);

    void deleteReview(CurrentUser currentUser,String reviewId);

    Object updateReview(CurrentUser currentUser, ReviewUpdateReq rq);

    Object checkExistReviewProduct(CurrentUser currentUser, CheckReview req);

    Object checkExistProductReview(CurrentUser currentUser, String id );

    Object checkExistProductReviewV2(String orderId,String productId);
}
