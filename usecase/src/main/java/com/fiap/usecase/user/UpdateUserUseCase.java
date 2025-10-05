package com.fiap.usecase;

import com.fiap.core.domain.User;
import com.fiap.core.exception.NotFoundException;

public interface UpdateUserUseCase {
    User execute(User user) throws NotFoundException;
}
