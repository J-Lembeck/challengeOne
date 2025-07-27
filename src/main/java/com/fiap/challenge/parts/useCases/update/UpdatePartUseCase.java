package com.fiap.challenge.parts.useCases.update;

import java.util.UUID;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.dto.UpdatePartRequestDTO;

public interface UpdatePartUseCase {
    PartResponseDTO execute(UUID id, UpdatePartRequestDTO partRequest);
}	