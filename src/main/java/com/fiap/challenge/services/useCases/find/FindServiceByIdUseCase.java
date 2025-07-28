package com.fiap.challenge.services.useCases.find;

import java.util.UUID;

import com.fiap.challenge.services.dto.ServiceResponseDTO;

public interface FindServiceByIdUseCase {

	public ServiceResponseDTO execute(UUID id);
}
