package com.fiap.challenge.vehicles.useCases.delete;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.exception.vehicle.VehicleNotFoundException;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleRepository vehicleRepository;

    @Override
    public void execute(UUID id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException(id);
        }

        vehicleRepository.deleteById(id);
    }
}