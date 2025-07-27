package com.fiap.challenge.parts.useCases.find;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.shared.exception.part.PartNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPartByIdUseCaseImpl implements FindPartByIdUseCase {

    private final PartsRepository partRepository;

    @Override
    public PartResponseDTO execute(UUID id) {
        return partRepository.findById(id)
                .map(part -> new PartResponseDTO(
                        part.getId(),
                        part.getName(),
                        part.getDescription(),
                        part.getPrice(),
                        part.getStockQuantity(),
                        part.getReservedStock(),
                        part.getMinimumStock(),
                        part.getCreatedAt(),
                        part.getUpdatedAt()
                ))
                .orElseThrow(() -> new PartNotFoundException(id));
    }
}