package com.fiap.challenge.services.useCases.create;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;

public interface CreateServiceUseCase {

	public ServiceResponseDTO execute(InputServiceDTO createServiceDTO);
}
