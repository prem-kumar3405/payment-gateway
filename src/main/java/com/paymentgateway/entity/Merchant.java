package com.paymentgateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "merchants")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "api_key",nullable = false,unique = true)
    private String apiKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
