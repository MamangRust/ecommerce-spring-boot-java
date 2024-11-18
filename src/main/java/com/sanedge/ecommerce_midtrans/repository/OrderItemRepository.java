package com.sanedge.ecommerce_midtrans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.OrderItems;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
    
}
