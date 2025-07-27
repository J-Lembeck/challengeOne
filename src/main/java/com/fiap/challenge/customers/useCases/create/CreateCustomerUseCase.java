package com.fiap.challenge.customers.useCases.create;

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;

public interface CreateCustomerUseCase {
    public CustomerResponseDTO execute(CreateCustomerRequestDTO request);
}