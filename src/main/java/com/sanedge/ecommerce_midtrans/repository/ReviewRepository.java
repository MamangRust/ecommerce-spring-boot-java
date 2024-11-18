package com.sanedge.ecommerce_midtrans.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.Review;



@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserIdAndProductId(long userId, long productId);

    List<Review> findAllByProductId(long productId);
}

