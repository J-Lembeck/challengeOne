package com.fiap.dto.response;

import com.fiap.core.domain.DocumentNumber;

import java.util.UUID;

public record CustomerResponse (
        UUID id,
        String name,
        String email,
        DocumentNumber documentNumber) {
}
