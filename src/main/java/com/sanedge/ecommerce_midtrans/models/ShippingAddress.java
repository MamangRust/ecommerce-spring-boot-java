package com.sanedge.ecommerce_midtrans.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
public class ShippingAddress extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alamat", nullable = false)
    private String alamat;

    @Column(name = "provinsi", nullable = false)
    private String provinsi;

    @Column(name = "negara", nullable = false)
    private String negara;

    @Column(name = "kota", nullable = false)
    private String kota;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
