package com.paymentgateway.service;

import com.paymentgateway.entity.Payment;
import com.paymentgateway.entity.PaymentTransaction;
import com.paymentgateway.enums.TransactionStatus;
import com.paymentgateway.exception.PaymentTransactionNotFoundException;
import com.paymentgateway.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public List<PaymentTransaction> getTransactionsByPaymentId(Long paymentId) {

        return paymentTransactionRepository.findByPaymentId(paymentId);
    }
    public PaymentTransaction getLatestTransactionByPaymentId(Long paymentId) {

        return paymentTransactionRepository
                .findFirstByPaymentIdOrderByCreatedAtDesc(paymentId)
                .orElseThrow(() ->
                        new PaymentTransactionNotFoundException("Payment transaction not found"));
    }

    private String generateTransactionReference() {
        return "TXN_" + UUID.randomUUID();
    }
}