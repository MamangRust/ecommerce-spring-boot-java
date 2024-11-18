package com.sanedge.ecommerce_midtrans.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM users u")
    int countUsers();

    Optional<User> findById(int id);
}
