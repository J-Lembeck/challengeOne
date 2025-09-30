package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.CreateUserGateway;
import com.fiap.core.domain.User;
import com.fiap.usecase.CreateUserUseCase;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    final CreateUserGateway createUserGateway;

    public CreateUserUseCaseImpl(CreateUserGateway createUserGateway) {
        this.createUserGateway = createUserGateway;

    }

    @Override
    public void execute(User user) {
        if(!createUserGateway.create(user)) {
            throw new RuntimeException("Failed to create user");
        }
    }
}
