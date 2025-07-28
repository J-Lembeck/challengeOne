package com.fiap.challenge.vehicles.useCases.find.byPlate;

import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehicleByPlateUseCase {

	public VehicleResponseDTO execute(String plate);
}
