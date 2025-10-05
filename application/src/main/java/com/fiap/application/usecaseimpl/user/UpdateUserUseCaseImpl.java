package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.UserGateway;
import com.fiap.core.domain.User;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.UpdateUserUseCase;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    final UserGateway userGateway;

    public UpdateUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;

    }

    @Override
    public User execute(User user) throws NotFoundException {
        var userUpdated = userGateway.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USE0007.getMessage(), ErrorCodeEnum.USE0007.getCode()));

        return userGateway.update(user);
    }
}
