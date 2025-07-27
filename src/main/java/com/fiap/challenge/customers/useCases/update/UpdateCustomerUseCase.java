package com.fiap.challenge.customers.useCases.update;

import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;

public interface UpdateCustomerUseCase {
    public CustomerResponseDTO execute(UUID id, UpdateCustomerRequestDTO request);
}