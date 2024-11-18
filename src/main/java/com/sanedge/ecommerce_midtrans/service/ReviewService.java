package com.sanedge.ecommerce_midtrans.service;


import com.sanedge.ecommerce_midtrans.domain.request.review.CreateReviewRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;


public interface ReviewService {
    public MessageResponse getAllReviews();
    public MessageResponse getReviewById(int reviewId);
    public MessageResponse createReview(CreateReviewRequest request);
}
