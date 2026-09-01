package com.paymentgateway.controller;

import com.paymentgateway.dto.CreateOrderRequest;
import com.paymentgateway.dto.OrderResponse;
import com.paymentgateway.entity.Order;
import com.paymentgateway.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(request);

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getMerchant().getId(),
                order.getCustomer().getId(),
                order.getAmount(),
                order.getCurrency(),
                order.getStatus(),
                order.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}