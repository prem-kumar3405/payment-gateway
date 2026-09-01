package com.paymentgateway.service;

import com.paymentgateway.dto.CreateOrderRequest;
import com.paymentgateway.entity.Customer;
import com.paymentgateway.entity.Merchant;
import com.paymentgateway.entity.Order;
import com.paymentgateway.enums.OrderStatus;
import com.paymentgateway.repository.CustomerRepository;
import com.paymentgateway.repository.MerchantRepository;
import com.paymentgateway.repository.OrderRepository;
import com.paymentgateway.exception.CustomerNotFoundException;
import com.paymentgateway.exception.MerchantNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MerchantRepository merchantRepository;
    private final CustomerRepository customerRepository;

    public OrderService(
            OrderRepository orderRepository,
            MerchantRepository merchantRepository,
            CustomerRepository customerRepository) {

        this.orderRepository = orderRepository;
        this.merchantRepository = merchantRepository;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(CreateOrderRequest request) {

        Merchant merchant = merchantRepository.findById(request.merchantId())
                .orElseThrow(() ->
                        new MerchantNotFoundException("Merchant not found"));

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        Order order = Order.builder()
                .merchant(merchant)
                .customer(customer)
                .amount(request.amount())
                .currency(request.currency().toUpperCase())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }
}