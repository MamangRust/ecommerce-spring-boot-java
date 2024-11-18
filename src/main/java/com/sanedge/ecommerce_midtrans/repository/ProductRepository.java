package com.sanedge.ecommerce_midtrans.repository;

import com.sanedge.ecommerce_midtrans.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @SuppressWarnings("null")
    @Override
    List<Product> findAll();

    
    @SuppressWarnings("null")
    Optional<Product> findById(Long productId);


    Optional<Product> findBySlugProduct(String slug);

    @Query("SELECT COUNT(p) FROM Product p")
    int countProducts();

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.countInStock = :quantity WHERE p.id = :productId")
    int updateProductQuantity(Long productId, int quantity);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId")
    void deleteProductById(Long productId);
}
