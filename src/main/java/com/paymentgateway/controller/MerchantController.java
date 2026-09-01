package com.paymentgateway.controller;

import com.paymentgateway.dto.CreateMerchantRequest;
import com.paymentgateway.dto.MerchantResponse;
import com.paymentgateway.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<MerchantResponse> createMerchant(
            @Valid @RequestBody CreateMerchantRequest request) {

        MerchantResponse response = merchantService.createMerchant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}