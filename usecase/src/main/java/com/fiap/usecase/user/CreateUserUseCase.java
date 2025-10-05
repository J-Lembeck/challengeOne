package com.fiap.usecase;

import com.fiap.core.domain.User;
import com.fiap.core.exception.InternalServerErrorException;

public interface CreateUserUseCase {
    User execute(User user) throws InternalServerErrorException;
}
