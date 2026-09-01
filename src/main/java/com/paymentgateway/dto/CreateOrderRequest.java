package com.paymentgateway.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateOrderRequest(

        @NotNull(message = "Merchant ID is required")
        @Positive(message = "Merchant ID must be positive")
        Long merchantId,

        @NotNull(message = "Customer ID is required")
        @Positive(message = "Customer ID must be positive")
        Long customerId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
        String currency
) {
}