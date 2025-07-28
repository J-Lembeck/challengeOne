package com.fiap.challenge.services.useCases.create;

import org.springframework.stereotype.Service;

import com.fiap.challenge.services.dto.InputServiceDTO;
import com.fiap.challenge.services.dto.ServiceResponseDTO;
import com.fiap.challenge.services.entity.ServiceModel;
import com.fiap.challenge.services.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateServiceUseCaseImpl implements CreateServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceResponseDTO execute(InputServiceDTO createServiceDTO) {
        var serviceModel = ServiceModel.builder()
                .name(createServiceDTO.name())
                .description(createServiceDTO.description())
                .basePrice(createServiceDTO.basePrice())
                .estimatedTimeMin(createServiceDTO.estimatedTimeMin())
                .build();

        var savedService = this.serviceRepository.save(serviceModel);

        return new ServiceResponseDTO(
                savedService.getId(),
                savedService.getName(),
                savedService.getDescription(),
                savedService.getBasePrice(),
                savedService.getEstimatedTimeMin(),
                savedService.getCreatedAt(),
                savedService.getUpdatedAt()
        );
    }
}