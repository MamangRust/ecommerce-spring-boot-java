package com.sanedge.ecommerce_midtrans.repository;

import com.sanedge.ecommerce_midtrans.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @SuppressWarnings("null")
    @Override
    List<Category> findAll();

    @SuppressWarnings("null")
    Optional<Category> findById(Long id);

    Optional<Category> findBySlug(String slugCategory);

    @SuppressWarnings("null")
    void deleteById(Long id);
}
