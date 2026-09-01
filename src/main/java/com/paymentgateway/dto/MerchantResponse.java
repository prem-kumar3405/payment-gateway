package com.paymentgateway.dto;

import java.time.LocalDateTime;

public record MerchantResponse(

        Long id,
        String name,
        String email,
        String apiKey,
        LocalDateTime createdAt
) {
}