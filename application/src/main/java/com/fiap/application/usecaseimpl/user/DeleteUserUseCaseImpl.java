package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.UserGateway;
import com.fiap.core.domain.User;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.DeleteUserUseCase;

import java.util.UUID;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserGateway userGateway;

    public DeleteUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException {
        User user = userGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USE0007.getMessage(), ErrorCodeEnum.USE0007.getCode()));

        userGateway.delete(user);

    }
}
