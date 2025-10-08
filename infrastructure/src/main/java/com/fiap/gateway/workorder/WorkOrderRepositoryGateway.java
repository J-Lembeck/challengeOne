package com.fiap.gateway.workorder;

import com.fiap.application.gateway.workorder.WorkOrderGateway;
import com.fiap.core.domain.workorder.WorkOrder;
import com.fiap.mapper.workorder.WorkOrderMapper;
import com.fiap.persistence.entity.workOrder.WorkOrderEntity;
import com.fiap.persistence.repository.workorder.WorkOrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class WorkOrderRepositoryGateway implements WorkOrderGateway {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    public WorkOrderRepositoryGateway(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
    }

    @Transactional
    @Override
    public void save(WorkOrder workOrder) {
        WorkOrderEntity workOrderEntity = workOrderRepository.save(workOrderMapper.toEntity(workOrder));
        workOrderMapper.toDomain(workOrderEntity);
    }

    @Transactional
    @Override
    public WorkOrder create(WorkOrder workOrder) {
        WorkOrderEntity workOrderEntity = workOrderRepository.save(workOrderMapper.toEntity(workOrder));
        return workOrderMapper.toDomain(workOrderEntity);
    }

    @Transactional
    @Override
    public Optional<WorkOrder> findById(UUID workOrderId) {
        Optional<WorkOrderEntity> workOrderEntity = workOrderRepository.findById(workOrderId);

        return workOrderEntity.map(workOrderMapper::toDomain);
    }

    @Transactional
    @Override
    public WorkOrder update(WorkOrder workOrder) {
        WorkOrderEntity entity = workOrderMapper.toEntity(workOrder);
        WorkOrderEntity saved = workOrderRepository.save(entity);
        return workOrderMapper.toDomain(saved);
    }
}
