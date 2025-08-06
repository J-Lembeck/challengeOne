package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;

import java.util.UUID;

public interface FindWorkOrderByIdUseCase {
    WorkOrderModel execute(UUID id);
}
