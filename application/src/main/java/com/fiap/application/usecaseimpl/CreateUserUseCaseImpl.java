package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.CreateUserGateway;
import com.fiap.core.domain.User;
import com.fiap.usecase.CreateUserUseCase;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    final CreateUserGateway createUserGateway;
//    final PasswordValidateUserCase passwordValidateUserCase;

    public CreateUserUseCaseImpl(CreateUserGateway createUserGateway) {
//        this.passwordValidateUserCase = passwordValidateUserCase;
        this.createUserGateway = createUserGateway;

    }

    @Override
    public void execute(User user) {
//        if(!passwordValidateUserCase.validatePassword(user.getPassword().getValue())) {
//          throw new PasswordException(ErrorCodeEnum.CAD0011.getMessage(), ErrorCodeEnum.CAD0011.getCode());
//        }

        if(!createUserGateway.create(user)) {
            throw new RuntimeException("Failed to create user");
        }
    }
}
