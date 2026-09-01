package com.paymentgateway.service;

import com.paymentgateway.dto.CreateMerchantRequest;
import com.paymentgateway.dto.MerchantResponse;
import com.paymentgateway.entity.Merchant;
import com.paymentgateway.exception.MerchantAlreadyExistsException;
import com.paymentgateway.repository.MerchantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;


    public MerchantResponse createMerchant(CreateMerchantRequest request) {

        if (merchantRepository.findByEmail(request.email()).isPresent()) {
            throw new MerchantAlreadyExistsException(
                    "Merchant email already exists"
            );
        }


        Merchant merchant = Merchant.builder()
                .name(request.name())
                .email(request.email())
                .apiKey(generateApiKey())
                .createdAt(LocalDateTime.now())
                .build();

        Merchant savedMerchant = merchantRepository.save(merchant);
        return new MerchantResponse(
                savedMerchant.getId(),
                savedMerchant.getName(),
                savedMerchant.getEmail(),
                savedMerchant.getApiKey(),
                savedMerchant.getCreatedAt()
        );
    }
    private String generateApiKey() {
        return "pk_" + UUID.randomUUID();
    }

}
