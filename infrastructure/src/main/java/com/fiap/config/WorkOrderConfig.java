package com.fiap.config;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.application.gateway.part.PartGateway;
import com.fiap.application.gateway.service.ServiceGateway;
import com.fiap.application.gateway.user.UserGateway;
import com.fiap.application.gateway.vehicle.VehicleGateway;
import com.fiap.application.gateway.workorder.WorkOrderGateway;
import com.fiap.application.usecaseimpl.workorder.CreateWorkOrderUseCaseImpl;
import com.fiap.application.usecaseimpl.workorder.FindWorkOrderByIdUseCaseImpl;
import com.fiap.usecase.workorder.CreateWorkOrderUseCase;
import com.fiap.usecase.workorder.FindWorkOrderByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkOrderConfig {

    @Bean
    public CreateWorkOrderUseCase createWorkOrderUseCase(WorkOrderGateway workOrderGateway,CustomerGateway customerGateway, VehicleGateway vehicleGateway, UserGateway userGateway, PartGateway partGateway, ServiceGateway serviceGateway) {
        return new CreateWorkOrderUseCaseImpl(workOrderGateway, customerGateway, vehicleGateway, userGateway, partGateway, serviceGateway);
    }

    @Bean
    public FindWorkOrderByIdUseCase findWorkOrderByIdUseCase(WorkOrderGateway workOrderGateway) {
        return new FindWorkOrderByIdUseCaseImpl(workOrderGateway);

    }
}
