package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;

import java.util.UUID;

public interface AceptedOrRefuseWorkOrderUseCase {
    public StatusWorkOrderRespondeDTO execute(UUID workOrderId, boolean accepted);
}
