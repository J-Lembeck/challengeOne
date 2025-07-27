package com.fiap.challenge.users.usecases.create;

import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;

public interface CreateUserUseCase {

	public UserResponseDTO execute(CreateUserRequestDTO requestDTO);
}