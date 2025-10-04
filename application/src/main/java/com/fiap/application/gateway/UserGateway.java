package com.fiap.application.gateway;

import com.fiap.core.domain.User;
import com.fiap.core.exception.NotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    User create(User user);
    User update(User user);
    Optional<User> findById(UUID userId) throws NotFoundException;
    void delete(User user);
}
