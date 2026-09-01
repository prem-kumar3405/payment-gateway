package com.paymentgateway.service;

import com.paymentgateway.entity.Payment;
import com.paymentgateway.entity.PaymentTransaction;
import com.paymentgateway.enums.TransactionStatus;
import com.paymentgateway.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransactionService(
            PaymentTransactionRepository paymentTransactionRepository) {

        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    public PaymentTransaction createTransaction(Payment payment) {

        PaymentTransaction transaction = PaymentTransaction.builder()
                .transactionReference(generateTransactionReference())
                .payment(payment)
                .amount(payment.getAmount())
                .status(TransactionStatus.INITIATED)
                .createdAt(LocalDateTime.now())
                .build();

        return paymentTransactionRepository.save(transaction);
    }

    private String generateTransactionReference() {
        return "TXN_" + UUID.randomUUID();
    }
}