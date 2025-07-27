package com.fiap.challenge.parts.useCases.find;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;

public interface FindPartsByIdsUseCase {
	public List<PartResponseDTO> execute(List<UUID> ids);
}
