package com.fiap.challenge.services.useCases.update;

import java.util.UUID;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;

public interface UpdateServiceUseCase {

	public ServiceResponseDTO execute(UUID id, InputServiceDTO updateServiceDTO);
}
