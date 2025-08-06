package com.fiap.challenge.workOrders.entity.enums;

public enum WorkOrderStatus {
	RECEIVED,
    IN_DIAGNOSIS,
    AWAITING_APPROVAL,
    REFUSED,
    IN_PROGRESS,
    COMPLETED,
    DELIVERED;

    public static WorkOrderStatus fromString(String status) {
        try {
            return WorkOrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}