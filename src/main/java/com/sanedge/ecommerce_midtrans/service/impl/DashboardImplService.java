package com.sanedge.ecommerce_midtrans.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.ecommerce_midtrans.domain.response.MessageResponse;
import com.sanedge.ecommerce_midtrans.domain.response.dashboard.DashboardResponse;
import com.sanedge.ecommerce_midtrans.repository.OrderRepository;
import com.sanedge.ecommerce_midtrans.repository.ProductRepository;
import com.sanedge.ecommerce_midtrans.repository.UserRepository;
import com.sanedge.ecommerce_midtrans.service.DashboardService;


@Service
public class DashboardImplService implements DashboardService {
    private static final Logger logger = LoggerFactory.getLogger(DashboardImplService.class);

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public DashboardImplService(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse dashboard() {
        try {
            int productCount = countProduct();
            int orderCount = countOrder();
            int userCount = countUser();
            int totalRevenue = sumTotalPrice();
            List<Integer> yearlyRevenue = calculateYearlyRevenue();
    

            logger.info("Dashboard data retrieved successfully");
           
            DashboardResponse dashboard = DashboardResponse.builder()
                    .user(userCount)
                    .product(productCount)
                    .order(orderCount)
                    .pendapatan(totalRevenue)
                    .totalPendapatan(yearlyRevenue)
                    .build();
    
            
            return MessageResponse.builder()
                    .message("Dashboard data retrieved successfully")
                    .data(dashboard)
                    .statusCode(200) 
                    .build();
    
        } catch (Exception e) {
            logger.error("Error generating dashboard data", e);
    
            return MessageResponse.builder()
                    .message("Failed to retrieve dashboard data")
                    .data(null)
                    .statusCode(500)
                    .build();
        }
    }
    

    private int countProduct() {
        return this.productRepository.countProducts();
    }

    private int countOrder() {
        return this.orderRepository.countOrders();
    }

    private int countUser() {
        return this.userRepository.countUsers();
    }

    private int sumTotalPrice() {
        Integer total = this.orderRepository.sumTotalPrice();
        return total != null ? total : 0;
    }

    private List<Integer> calculateYearlyRevenue() {
        List<Integer> yearlyRevenue = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();

        for (int month = 1; month <= 12; month++) {
            Integer monthlyRevenue = this.orderRepository.calculateYearlyRevenue(currentYear);
            yearlyRevenue.add(monthlyRevenue != null ? monthlyRevenue : 0);
        }

        return yearlyRevenue;
    }
}