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


    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE EXTRACT(YEAR FROM o.createdAt) = :year AND EXTRACT(MONTH FROM o.createdAt) = :month")
    Integer calculateMonthlyRevenue(int year, int month);
    
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o")
    Integer sumTotalPrice();
}