package com.fiap.challenge.vehicles.useCases.find.byClientCpf;

import java.util.List;

import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;

public interface FindVehiclesByCustomerDocumentUseCase {

	public List<VehicleResponseDTO> execute(String cpfCnpj);
}
