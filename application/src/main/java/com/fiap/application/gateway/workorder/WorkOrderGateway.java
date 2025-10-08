package com.fiap.application.gateway.workorder;

import com.fiap.core.domain.workorder.WorkOrder;

import java.util.Optional;
import java.util.UUID;

public interface WorkOrderGateway {

    void save(WorkOrder workOrder);
    WorkOrder create(WorkOrder workOrder);
    Optional<WorkOrder> findById(UUID workOrderId);
}
