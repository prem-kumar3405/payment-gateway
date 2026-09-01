package com.paymentgateway.dto;

import jakarta.validation.constraints.NotNull;

public record CompletePaymentRequest(

        @NotNull(message = "Success status is required")
        Boolean success
) {
}