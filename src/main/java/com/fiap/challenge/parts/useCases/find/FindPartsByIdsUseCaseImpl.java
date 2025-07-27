package com.fiap.challenge.parts.useCases.find;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPartsByIdsUseCaseImpl implements FindPartsByIdsUseCase {

    private final PartsRepository partRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PartResponseDTO> execute(List<UUID> ids) {
        List<PartModel> foundParts = partRepository.findAllById(ids);

        return foundParts.stream()
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
                .collect(Collectors.toList());
    }
}