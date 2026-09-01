package com.paymentgateway.service;

import com.paymentgateway.dto.CreatePaymentRequest;
import com.paymentgateway.entity.Order;
import com.paymentgateway.entity.Payment;
import com.paymentgateway.repository.OrderRepository;
import com.paymentgateway.repository.PaymentRepository;
import com.paymentgateway.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment createPayment(CreatePaymentRequest request) {

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));

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

        return paymentRepository.save(payment);
    }

    private String generatePaymentReference() {
        return "PAY_" + UUID.randomUUID();
    }
}