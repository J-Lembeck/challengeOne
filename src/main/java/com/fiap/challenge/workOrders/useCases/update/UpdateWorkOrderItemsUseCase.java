package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;

import java.util.UUID;

public interface UpdateWorkOrderItemsUseCase {

    public WorkOrderResumeDTO execute(UUID id, WorkOrderItemDTO workOrderItemDTO);
}
