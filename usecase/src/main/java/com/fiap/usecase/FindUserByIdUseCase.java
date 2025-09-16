package com.fiap.usecase;

import com.fiap.core.domain.User;
import java.util.UUID;

public interface FindUserByIdUseCase {
    User execute(UUID userId);
}
