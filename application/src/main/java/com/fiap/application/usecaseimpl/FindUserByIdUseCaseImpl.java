package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.UserGateway;
import com.fiap.core.domain.User;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.FindUserByIdUseCase;

import java.util.Optional;
import java.util.UUID;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    final UserGateway userGateway;

    public FindUserByIdUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Optional<User> execute(UUID userId) throws NotFoundException {
        return userGateway.findById(userId);
    }
}
