package com.fiap.challenge.customers.useCases.find;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;

public interface FindCustomerByCpfCnpj {
    public CustomerResponseDTO execute(String cpfCnpj);
}