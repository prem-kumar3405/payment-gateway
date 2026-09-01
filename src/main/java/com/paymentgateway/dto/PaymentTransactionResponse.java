package com.paymentgateway.dto;

import com.paymentgateway.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentTransactionResponse(

        Long id,
        String transactionReference,
        Long paymentId,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {
}