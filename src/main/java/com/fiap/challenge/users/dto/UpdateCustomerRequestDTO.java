package com.fiap.challenge.users.dto;

public record UpdateCustomerRequestDTO(
    String name,
    String cpfCnpj,
    String phone,
    String email
) {}