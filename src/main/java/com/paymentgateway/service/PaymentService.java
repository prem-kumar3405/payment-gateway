package com.paymentgateway.service;

import com.paymentgateway.dto.CreatePaymentRequest;
import com.paymentgateway.entity.Order;
import com.paymentgateway.entity.Payment;
import com.paymentgateway.enums.TransactionStatus;
import com.paymentgateway.exception.PaymentNotFoundException;
import com.paymentgateway.exception.PaymentNotProcessableException;
import com.paymentgateway.repository.OrderRepository;
import com.paymentgateway.repository.PaymentRepository;
import com.paymentgateway.enums.PaymentStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.paymentgateway.exception.OrderNotFoundException;
import com.paymentgateway.entity.PaymentTransaction;



import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentTransactionService paymentTransactionService;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,PaymentTransactionService paymentTransactionService) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentTransactionService = paymentTransactionService;
    }

    @Transactional
    public Payment createPayment(CreatePaymentRequest request) {

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found"));

        Payment payment = Payment.builder()
                .paymentReference(generatePaymentReference())
                .order(order)
                .amount(order.getAmount())
                .currency(order.getCurrency())
                .paymentMethod(request.paymentMethod())
                .status(PaymentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        paymentTransactionService.createTransaction(savedPayment);

        return savedPayment;
    }
    public Payment getPaymentById(Long paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));
    }
    @Transactional
    public Payment processPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.CREATED) {
            throw new PaymentNotProcessableException(
                    "Payment cannot be processed from status: "
                            + payment.getStatus()
            );
        }

        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
    public Payment getPaymentByReference(String paymentReference) {

        return paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));
    }
    @Transactional
    public Payment completePayment(
            Long paymentId,
            boolean success) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.PROCESSING) {
            throw new PaymentNotProcessableException(
                    "Payment cannot be completed from status: "
                            + payment.getStatus()
            );
        }

        if (success) {
            payment.setStatus(PaymentStatus.SUCCESS);

            paymentTransactionService.updateLatestTransactionStatus(
                    paymentId,
                    TransactionStatus.SUCCESS
            );
        } else {
            payment.setStatus(PaymentStatus.FAILED);

            paymentTransactionService.updateLatestTransactionStatus(
                    paymentId,
                    TransactionStatus.FAILED
            );
        }

        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    private String generatePaymentReference() {
        return "PAY_" + UUID.randomUUID();
    }
}