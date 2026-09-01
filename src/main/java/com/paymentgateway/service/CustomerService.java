package com.paymentgateway.service;

import com.paymentgateway.dto.CreateCustomerRequest;
import com.paymentgateway.dto.CustomerResponse;
import com.paymentgateway.entity.Customer;
import com.paymentgateway.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getCreatedAt()
        );
    }
}