package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.FindUserByIdGateway;
import com.fiap.core.domain.User;
import com.fiap.core.exception.EmailException;
import com.fiap.usecase.FindUserByIdUseCase;

import java.util.UUID;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    final FindUserByIdGateway findUserByIdGateway;

    public FindUserByIdUseCaseImpl(FindUserByIdGateway findUserByIdGateway) {
        this.findUserByIdGateway = findUserByIdGateway;
    }

    @Override
    public User execute(UUID userId) {
        return findUserByIdGateway.findById(userId);
    }
}
