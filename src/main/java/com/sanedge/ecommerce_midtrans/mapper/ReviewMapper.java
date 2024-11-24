package com.sanedge.ecommerce_midtrans.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sanedge.ecommerce_midtrans.domain.response.review.ReviewResponse;
import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.models.Review;

@Component
public class ReviewMapper {

    public ReviewResponse toReviewResponse(Review review) {
        if (review == null) {
            return null;
        }

        return ReviewResponse.builder()
                .id(review.getId().intValue())
                .name(review.getName())
                .comment(review.getComment())
                .rating(review.getRating())
                .user(UserResponse.builder()
                        .id(review.getUser().getId().intValue())
                        .name(review.getUser().getName())
                        .email(review.getUser().getEmail())
                        .isStaff(review.getUser().isStaff())
                        .createdAt(review.getUser().getCreatedAt() != null ? review.getUser().getCreatedAt().toString() : null)
                        .updatedAt(review.getUser().getUpdatedAt() != null ? review.getUser().getUpdatedAt().toString() : null)
                        .build())

                .productId(review.getProduct().getId().intValue())
                .build();
    }

    public List<ReviewResponse> toReviewResponses(List<Review> reviews) {
        return reviews.stream()
                .map(this::toReviewResponse)
                .collect(Collectors.toList());
    }
}
