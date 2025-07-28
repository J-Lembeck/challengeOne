package com.fiap.challenge.vehicles.useCases.find;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindVehicleByIdUseCaseImpl implements FindVehicleByIdUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponseDTO execute(UUID id) {
        return vehicleRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    private VehicleResponseDTO convertToDto(VehicleModel model) {
        var customerInfo = new CustomerInfo(model.getCustomer().getId(), model.getCustomer().getName());
        return new VehicleResponseDTO(
                model.getId(),
                customerInfo,
                model.getLicensePlate(),
                model.getBrand(),
                model.getModel(),
                model.getYear(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}