package com.fiap.challenge.customers.dto;

public record CreateCustomerRequestDTO(
    String name,
    String cpfCnpj,
    String phone,
    String email
) {}
