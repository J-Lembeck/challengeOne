package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;

import java.util.UUID;

public interface UpdateStatusWorkOrderUseCase {
    public StatusWorkOrderRespondeDTO execute(UUID workOrderId, String status);
}
