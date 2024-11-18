package com.sanedge.ecommerce_midtrans.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @SuppressWarnings("null")
    @Override
    List<Order> findAll();

    // Get orders by user ID
    List<Order> findByUser_Id(Long userId);

    @SuppressWarnings("null")
    Optional<Order> findById(Long id);

    
    @Query("SELECT COUNT(o) FROM Order o")
    int countOrders();


    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE FUNCTION('YEAR', o.createdAt) = :year")
    Integer calculateYearlyRevenue(int year);

    
    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Integer sumTotalPrice();
}