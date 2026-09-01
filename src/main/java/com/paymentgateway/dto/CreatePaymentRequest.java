package com.paymentgateway.dto;

import com.paymentgateway.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(

        @NotNull(message = "Order ID is required")
        Long orderId,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod
) {
}