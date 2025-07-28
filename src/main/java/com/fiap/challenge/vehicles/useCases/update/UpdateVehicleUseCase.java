package com.fiap.challenge.vehicles.useCases.update;

import java.util.UUID;

import com.fiap.challenge.vehicles.dto.InputVehicleDTO;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface UpdateVehicleUseCase {
	public VehicleResponseDTO execute(UUID vehicleId, InputVehicleDTO dto);
}
