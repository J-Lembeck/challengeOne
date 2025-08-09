package com.fiap.challenge.workOrders.history.dto;

import java.util.UUID;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;

import jakarta.annotation.Nullable;

public record UpdateWorkOrderStatusCommand(
	UUID workOrderId, 
	WorkOrderStatus newStatus,
	@Nullable String notes
) {

    public UpdateWorkOrderStatusCommand(UUID workOrderId, WorkOrderStatus newStatus) {
        this(workOrderId, newStatus, null);
    }
}