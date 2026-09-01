package com.paymentgateway.repository;

import com.paymentgateway.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findByPaymentId(Long paymentId);
    Optional<PaymentTransaction> findFirstByPaymentIdOrderByCreatedAtDesc(Long paymentId);
}