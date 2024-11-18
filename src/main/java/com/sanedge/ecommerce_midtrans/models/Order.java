package com.sanedge.ecommerce_midtrans.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Order extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "courier", nullable = false)
    private String courier;
    
    @Column(name = "shippingMethod", nullable = false)
    private String shippingMethod;
    
    @Column(name = "shippingCost", nullable = false)
    private int shippingCost;
    
    @Column(name = "totalProduct", nullable = false)
    private String totalProduct;
    
    @Column(name = "totalPrice", nullable = false)
    private int totalPrice;
    
    @Column(name = "transactionId", nullable = false)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;

    @OneToOne(mappedBy = "order")
    private ShippingAddress shippingAddress;
}
