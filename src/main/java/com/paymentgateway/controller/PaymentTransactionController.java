package com.paymentgateway.controller;

import com.paymentgateway.dto.PaymentTransactionResponse;
import com.paymentgateway.entity.PaymentTransaction;
import com.paymentgateway.service.PaymentTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-transactions")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    public PaymentTransactionController(
            PaymentTransactionService paymentTransactionService) {

        this.paymentTransactionService = paymentTransactionService;
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<PaymentTransactionResponse>> getTransactionsByPaymentId(
            @PathVariable Long paymentId) {

        List<PaymentTransaction> transactions =
                paymentTransactionService.getTransactionsByPaymentId(paymentId);

        List<PaymentTransactionResponse> response = transactions.stream()
                .map(transaction -> new PaymentTransactionResponse(
                        transaction.getId(),
                        transaction.getTransactionReference(),
                        transaction.getPayment().getId(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}