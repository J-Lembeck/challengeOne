package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.customer.FindCustomerByIdUseCase; // <-- Import the UseCase
import com.fiap.usecase.vehicle.FindVehicleByIdUseCase;
import com.fiap.usecase.vehicle.UpdateVehicleUseCase;

public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

    private final VehicleGateway vehicleGateway;
    private final FindVehicleByIdUseCase findVehicleByIdUseCase;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public UpdateVehicleUseCaseImpl(VehicleGateway vehicleGateway, FindVehicleByIdUseCase findVehicleByIdUseCase, FindCustomerByIdUseCase findCustomerByIdUseCase) {
        this.vehicleGateway = vehicleGateway;
        this.findVehicleByIdUseCase = findVehicleByIdUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
    }

    @Override
    public Vehicle execute(Vehicle vehicle) throws NotFoundException, DocumentNumberException {
        Vehicle existingVehicle = findVehicleByIdUseCase.execute(vehicle.getId());

        findCustomerByIdUseCase.execute(vehicle.getCustomer().getId());

        existingVehicle.setCustomer(vehicle.getCustomer());
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setBrand(vehicle.getBrand());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setYear(vehicle.getYear());

        return vehicleGateway.update(existingVehicle);
    }
}