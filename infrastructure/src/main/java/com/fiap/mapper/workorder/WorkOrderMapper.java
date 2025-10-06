package com.fiap.mapper.workorder;

import com.fiap.core.domain.workorder.WorkOrder;
import com.fiap.core.domain.workorder.WorkOrderPart;
import com.fiap.core.domain.workorder.WorkOrderService;
import com.fiap.core.exception.BadRequestException;
import com.fiap.dto.workorder.CreateWorkOrderRequest;
import com.fiap.dto.workorder.WorkOrderPartResponse;
import com.fiap.dto.workorder.WorkOrderResponse;
import com.fiap.dto.workorder.WorkOrderServiceResponse;
import com.fiap.mapper.customer.CustomerMapper;
import com.fiap.mapper.user.UserMapper;
import com.fiap.mapper.vehicle.VehicleMapper;
import com.fiap.persistence.entity.workOrder.WorkOrderEntity;
import com.fiap.persistence.entity.workOrder.WorkOrderPartEntity;
import com.fiap.persistence.entity.workOrder.WorkOrderServiceEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkOrderMapper {

    private final CustomerMapper customerMapper;
    private final VehicleMapper vehicleMapper;
    private final UserMapper userMapper;
    private final WorkOrderServiceMapper workOrderServiceMapper;
    private final WorkOrderPartMapper workOrderPartMapper;

    public WorkOrderMapper(CustomerMapper customerMapper, VehicleMapper vehicleMapper, UserMapper userMapper, WorkOrderServiceMapper workOrderServiceMapper, WorkOrderPartMapper workOrderPartMapper) {
        this.customerMapper = customerMapper;
        this.vehicleMapper = vehicleMapper;
        this.userMapper = userMapper;
        this.workOrderServiceMapper = workOrderServiceMapper;
        this.workOrderPartMapper = workOrderPartMapper;
    }

    public WorkOrderEntity toEntity(WorkOrder workOrder) {
        List<WorkOrderPartEntity> workOrderPartEntities = workOrder.getWorkOrderParts().stream()
                .map(workOrderPartMapper::toEntity)
                .toList();

        List<WorkOrderServiceEntity> workOrderServiceEntities = workOrder.getWorkOrderServices().stream()
                .map(workOrderServiceMapper::toEntity)
                .toList();

        WorkOrderEntity workOrderEntity = new WorkOrderEntity(
                workOrder.getId(),
                customerMapper.toEntity(workOrder.getCustomer()),
                vehicleMapper.toEntity(workOrder.getVehicle()),
                userMapper.toEntity(workOrder.getCreatedBy()),
                null,
                workOrderPartEntities,
                workOrderServiceEntities,
                workOrder.getStatus(),
                workOrder.getTotalAmount(),
                workOrder.getFinishedAt(),
                workOrder.getCreatedAt(),
                workOrder.getUpdatedAt()
        );

        workOrderEntity.getWorkOrderPartEntities()
                .forEach(part -> part.setWorkOrder(workOrderEntity));
        workOrderEntity.getWorkOrderServiceEntities()
                .forEach(service -> service.setWorkOrder(workOrderEntity));

        return workOrderEntity;
    }

    public WorkOrder toDomain(WorkOrderEntity workOrderEntity) {
        List<WorkOrderPart> workOrderParts = workOrderEntity.getWorkOrderPartEntities().stream()
                .map(workOrderPartMapper::toDomain)
                .toList();

        List<WorkOrderService> workOrderServices = workOrderEntity.getWorkOrderServiceEntities().stream()
                .map(workOrderServiceMapper::toDomain)
                .toList();

        return new WorkOrder(
                workOrderEntity.getId(),
                customerMapper.toDomain(workOrderEntity.getCustomer()),
                vehicleMapper.toDomain(workOrderEntity.getVehicle()),
                userMapper.toDomain(workOrderEntity.getCreatedBy()),
                workOrderParts,
                workOrderServices,
                workOrderEntity.getStatus(),
                workOrderEntity.getTotalAmount()
        );
    }

    public WorkOrder toDomain(CreateWorkOrderRequest request) throws BadRequestException {
        List<WorkOrderService> services = request.services() != null && !request.services().isEmpty() ? request.services()
                .stream().map(workOrderServiceMapper::toDomain)
                .toList() : List.of();

        List<WorkOrderPart> parts = request.parts() != null && !request.parts().isEmpty() ? request.parts()
                .stream().map(workOrderPartMapper::toDomain)
                .toList() : List.of();

        return new WorkOrder(
                request.customerId(),
                request.vehicleId(),
                request.createdById(),
                request.assignedMechanicId(),
                parts,
                services
        );
    }

    public WorkOrderResponse toResponse(WorkOrder workOrder) {
        List<WorkOrderServiceResponse> services = workOrder.getWorkOrderServices() != null && !workOrder.getWorkOrderServices().isEmpty() ? workOrder.getWorkOrderServices()
                .stream().map(workOrderServiceMapper::toResponse)
                .toList() : List.of();

        List<WorkOrderPartResponse> parts = workOrder.getWorkOrderParts() != null && !workOrder.getWorkOrderParts().isEmpty() ? workOrder.getWorkOrderParts()
                .stream().map(workOrderPartMapper::toResponse)
                .toList() : List.of();

        return new WorkOrderResponse(
                workOrder.getId(),
                workOrder.getCustomer().getId(),
                workOrder.getVehicle().getId(),
                workOrder.getCreatedBy().getId(),
                workOrder.getTotalAmount(),
                parts,
                services
        );
    }
}
