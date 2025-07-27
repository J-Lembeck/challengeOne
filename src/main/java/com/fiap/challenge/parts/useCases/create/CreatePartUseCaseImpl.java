package com.fiap.challenge.parts.useCases.create;

import org.springframework.stereotype.Service;

import com.fiap.challenge.parts.dto.CreatePartRequestDTO;
import com.fiap.challenge.parts.dto.PartResponseDTO;
import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.parts.repository.PartsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePartUseCaseImpl implements CreatePartUseCase{

	private final PartsRepository partRepository;

	@Override
    @Transactional
    public PartResponseDTO execute(CreatePartRequestDTO partRequest) {
        PartModel newPart = PartModel.builder()
                .name(partRequest.name())
                .description(partRequest.description())
                .price(partRequest.price())
                .stockQuantity(partRequest.stockQuantity())
                .minimumStock(partRequest.minimumStock())
                .build();

        PartModel savedPart = this.partRepository.save(newPart);

        return new PartResponseDTO(
            savedPart.getId(),
            savedPart.getName(),
            savedPart.getDescription(),
            savedPart.getPrice(),
            savedPart.getStockQuantity(),
            savedPart.getReservedStock(),
            savedPart.getMinimumStock(),
            savedPart.getCreatedAt(),
            savedPart.getUpdatedAt()
        );
    }

}
