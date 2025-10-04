package com.fiap.usecase;

import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id) throws NotFoundException;
}
