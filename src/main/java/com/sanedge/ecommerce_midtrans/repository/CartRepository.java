package com.sanedge.ecommerce_midtrans.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(Long userId);

    @SuppressWarnings("null")
    Optional<Cart> findById(Long id);

    @SuppressWarnings("null")
    void deleteById(Long id);

    int deleteByIdIn(List<Long> ids);
}
