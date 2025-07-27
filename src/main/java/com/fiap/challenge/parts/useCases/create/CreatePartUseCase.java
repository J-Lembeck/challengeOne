package com.fiap.challenge.parts.useCases.create;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;

public interface CreatePartUseCase {

	public PartResponseDTO execute(CreatePartRequestDTO partRequest);
}
 