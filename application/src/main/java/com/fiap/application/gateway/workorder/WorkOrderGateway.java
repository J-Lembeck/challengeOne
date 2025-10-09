package com.fiap.application.gateway.workorder;

import com.fiap.core.domain.workorder.WorkOrder;

import java.util.Optional;
import java.util.UUID;

public interface WorkOrderGateway {

    WorkOrder save(WorkOrder workOrder);
    Optional<WorkOrder> findById(UUID workOrderId);
    WorkOrder update(WorkOrder workOrder);
}
