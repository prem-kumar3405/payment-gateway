package com.paymentgateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMerchantRequest(

        @NotBlank(message = "Merchant name is required")
        String name,

        @NotBlank(message = "Merchant email is required")
        @Email(message = "Invalid email format")
        String email
) {
}