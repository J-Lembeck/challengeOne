package com.fiap.application.gateway;

import com.fiap.core.domain.User;

public interface CreateUserGateway {
    Boolean create(User user);
}
