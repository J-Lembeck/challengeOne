package com.fiap.gateway;

import com.fiap.application.gateway.WorkOrderGateway;
import com.fiap.core.domain.WorkOrder;
import com.fiap.mapper.WorkOrderMapper;
import com.fiap.persistence.entity.WorkOrderEntity;
import com.fiap.persistence.repository.WorkOrderRepository;

import java.util.Optional;
import java.util.UUID;

public class WorkOrderRepositoryGateway implements WorkOrderGateway {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    public WorkOrderRepositoryGateway(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
    }

    @Override
    public Optional<WorkOrder> findById(UUID customerId) {
        Optional<WorkOrderEntity> workOrderEntity = workOrderRepository.findById(customerId);

        return workOrderEntity.map(workOrderMapper::toDomain);
    }
}
