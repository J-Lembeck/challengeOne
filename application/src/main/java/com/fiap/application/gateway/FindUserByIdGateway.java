package com.fiap.application.gateway;

import com.fiap.core.domain.User;

import java.util.UUID;

public interface FindUserByIdGateway {
    User findById(UUID userId);
}
