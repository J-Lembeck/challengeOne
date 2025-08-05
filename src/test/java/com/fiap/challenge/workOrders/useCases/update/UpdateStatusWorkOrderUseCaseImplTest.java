package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStatusWorkOrderUseCaseImplTest {

    @Test
    void executeWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> { WorkOrderStatus.fromString("PENDING");
        });
    }

    @Test
    void executeWithValidStatus() {
        WorkOrderStatus status = WorkOrderStatus.fromString("IN_PROGRESS");
        assertEquals(WorkOrderStatus.IN_PROGRESS, status);
    }
}
