package com.paymentgateway.entity;

import com.paymentgateway.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_reference", nullable = false,unique = true)
    private String paymentReference;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal amount;

    @Column(nullable = false,length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    private Paymen

}
