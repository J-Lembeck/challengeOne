package com.fiap.challenge.customers.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;

public interface FindCustomersByIdsUseCase {
    public List<CustomerResponseDTO> execute(List<UUID> ids);
}