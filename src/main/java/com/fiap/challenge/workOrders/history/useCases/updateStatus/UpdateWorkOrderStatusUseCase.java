package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;

public interface UpdateWorkOrderStatusUseCase {
    public WorkOrderModel execute(UpdateWorkOrderStatusCommand command);
}
