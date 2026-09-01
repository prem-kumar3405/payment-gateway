package com.paymentgateway.dto;

import com.paymentgateway.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(

        Long id,
        Long merchantId,
        Long customerId,
        BigDecimal amount,
        String currency,
        OrderStatus status,
        LocalDateTime createdAt
) {
}