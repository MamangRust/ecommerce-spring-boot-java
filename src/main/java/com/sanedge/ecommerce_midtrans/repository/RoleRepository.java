package com.sanedge.ecommerce_midtrans.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.enums.ERole;
import com.sanedge.ecommerce_midtrans.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}