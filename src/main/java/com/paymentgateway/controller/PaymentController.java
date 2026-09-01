package com.paymentgateway.controller;

import com.paymentgateway.dto.CreatePaymentRequest;
import com.paymentgateway.dto.CompletePaymentRequest;
import com.paymentgateway.dto.PaymentResponse;
import com.paymentgateway.entity.Payment;
import com.paymentgateway.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        Payment payment = paymentService.createPayment(request);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long paymentId) {

        Payment payment = paymentService.getPaymentById(paymentId);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{paymentId}/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long paymentId) {

        Payment payment = paymentService.processPayment(paymentId);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponse> completePayment(
            @PathVariable Long paymentId,
            @Valid @RequestBody CompletePaymentRequest request) {

        Payment payment = paymentService.completePayment(
                paymentId,
                request.success()
        );

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getPaymentReference(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }
}