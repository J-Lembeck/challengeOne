package com.fiap.challenge.customers.useCases.find;

import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;

public interface FindCustomerByIdUseCase {
    public CustomerResponseDTO execute(UUID id);
}