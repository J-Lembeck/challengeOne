package com.fiap.challenge.vehicles.useCases.find.byClientCpf;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerInfo;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.vehicles.dto.VehicleResponseDTO;
import com.fiap.challenge.vehicles.entity.VehicleModel;
import com.fiap.challenge.vehicles.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindVehiclesByCustomerDocumentUseCaseImpl implements FindVehiclesByCustomerDocumentUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<VehicleResponseDTO> execute(String cpfCnpj) {
        if (!customerRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new CustomerNotFoundException("No client found with CPF/CNPJ: " + cpfCnpj);
        }

        return vehicleRepository.findByCustomerCpfCnpj(cpfCnpj)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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