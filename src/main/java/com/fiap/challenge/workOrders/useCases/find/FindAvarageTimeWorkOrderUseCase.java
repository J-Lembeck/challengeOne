package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.entity.WorkOrderAvarageTime;

import java.util.UUID;

public interface FindAvarageTimeWorkOrderUseCase {
    WorkOrderAvarageTime execute(UUID id);
}
