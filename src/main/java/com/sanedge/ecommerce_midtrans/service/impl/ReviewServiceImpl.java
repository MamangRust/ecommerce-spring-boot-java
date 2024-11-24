package com.sanedge.ecommerce_midtrans.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.request.review.CreateReviewRequest;
import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.review.ReviewResponse;
import com.sanedge.ecommerce_midtrans.domain.response.user.UserResponse;
import com.sanedge.ecommerce_midtrans.mapper.ReviewMapper;
import com.sanedge.ecommerce_midtrans.models.Product;
import com.sanedge.ecommerce_midtrans.models.Review;
import com.sanedge.ecommerce_midtrans.models.User;
import com.sanedge.ecommerce_midtrans.repository.ProductRepository;
import com.sanedge.ecommerce_midtrans.repository.ReviewRepository;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            ReviewMapper reviewMapper
            ) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public MessageResponse getAllReviews() {
        logger.info("Starting getAllReviews() method");
        try {
            List<Review> reviews = reviewRepository.findAll();
            if (reviews.isEmpty()) {
                logger.warn("No reviews found");
                return MessageResponse.builder()
                        .message("No reviews found")
                        .statusCode(404)
                        .build();
            }

            logger.info("Retrieved {} reviews successfully", reviews.size());
            List<ReviewResponse> reviewResponses = reviewMapper.toReviewResponses(reviews);

            return MessageResponse.builder()
                    .message("Reviews retrieved successfully")
                    .data(reviewResponses)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving reviews: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve reviews")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse getReviewById(int reviewId) {
        logger.info("Starting getReviewById() method with ID: {}", reviewId);
        try {
            long id = (long) reviewId;

            Review review = reviewRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            logger.info("Successfully retrieved review with ID: {}", reviewId);

            

            ReviewResponse reviewResponse = reviewMapper.toReviewResponse(review);

            return MessageResponse.builder()
                    .message("Review retrieved successfully")
                    .data(reviewResponse)
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving review by ID: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to retrieve review by ID")
                    .statusCode(500)
                    .build();
        }
    }

    @Override
    public MessageResponse createReview(CreateReviewRequest request) {
        logger.info("Starting createReview() method with request: {}", request);
        try {
            long userId = (long) request.getUserID();
            long productId = (long) request.getProductID();

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Optional<Review> existingReview = reviewRepository.findByUserIdAndProductId(
                    request.getUserID(), request.getProductID());
            if (existingReview.isPresent()) {
                logger.warn("Review already exists for user {} and product {}", request.getUserID(),
                        request.getProductID());
                return MessageResponse.builder()
                        .message("Review already exists")
                        .statusCode(409)
                        .build();
            }

            Review newReview = new Review();
            newReview.setName(user.getName());
            newReview.setUser(user);
            newReview.setRating(request.getRating());
            newReview.setComment(request.getComment());
            newReview.setProduct(product);

            Review savedReview = reviewRepository.save(newReview);
            logger.info("Review created successfully with ID: {}", savedReview.getId());

            List<Review> productReviews = reviewRepository.findAllByProductId(request.getProductID());
            double averageRating = productReviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            product.setRating(averageRating);
            productRepository.save(product);

            logger.info("Product with ID {} updated with new rating: {}", request.getProductID(), averageRating);

            ReviewResponse reviewResponse = reviewMapper.toReviewResponse(savedReview);

            return MessageResponse.builder()
                    .message("Review created successfully")
                    .data(reviewResponse)
                    .statusCode(201)
                    .build();
        } catch (Exception e) {
            logger.error("Error occurred while creating review: {}", e.getMessage());
            return MessageResponse.builder()
                    .message("Failed to create review")
                    .statusCode(500)
                    .build();
        }
    }
}