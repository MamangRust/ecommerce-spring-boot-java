package com.sanedge.ecommerce_midtrans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.ecommerce_midtrans.models.Slider;

@Repository
public interface SliderRepository  extends JpaRepository<Slider, Long>{
    
}
