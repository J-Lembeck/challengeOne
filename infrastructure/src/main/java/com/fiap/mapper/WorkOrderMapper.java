package com.fiap.mapper;

import com.fiap.core.domain.WorkOrder;
import com.fiap.persistence.entity.WorkOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderMapper {

    private final CustomerMapper customerMapper;

    public WorkOrderMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public WorkOrderEntity toEntity(WorkOrder workOrder) {
        return new WorkOrderEntity(
                workOrder.getId(),
                customerMapper.toEntity(workOrder.getCustomer()),
                workOrder.getStatus(),
                workOrder.getTotalAmount(),
                workOrder.getFinishedAt(),
                workOrder.getCreatedAt(),
                workOrder.getUpdatedAt()
        );
    }

    public WorkOrder toDomain(WorkOrderEntity workOrderEntity) {
        return new WorkOrder(
                workOrderEntity.getId(),
                customerMapper.toDomain(workOrderEntity.getCustomer()),
                workOrderEntity.getStatus()
        );
    }
}
