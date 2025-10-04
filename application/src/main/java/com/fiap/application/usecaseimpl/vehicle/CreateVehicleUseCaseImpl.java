package com.fiap.application.usecaseimpl.vehicle;

import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.core.domain.vehicle.Vehicle;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.usecase.customer.FindCustomerByIdUseCase;
import com.fiap.usecase.vehicle.CreateVehicleUseCase;

public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleGateway vehicleGateway;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public CreateVehicleUseCaseImpl(VehicleGateway vehicleGateway, FindCustomerByIdUseCase findCustomerByIdUseCase) {
        this.vehicleGateway = vehicleGateway;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
    }

    @Override
    public Vehicle execute(Vehicle vehicle) throws NotFoundException, DocumentNumberException {
        findCustomerByIdUseCase.execute(vehicle.getCustomer().getId());

        return vehicleGateway.create(vehicle);
    }
}