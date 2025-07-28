package com.fiap.challenge.services.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.services.dto.ServiceResponseDTO;

public interface FindServicesByIdsUseCase {

	public List<ServiceResponseDTO> execute(List<UUID> ids);
}
