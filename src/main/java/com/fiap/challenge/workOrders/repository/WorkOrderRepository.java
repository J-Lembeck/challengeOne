package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.entity.WorkOrder;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {

    List<WorkOrder> findByStatus(WorkOrderStatus status);

    List<WorkOrder> findByCustomerId(UUID customerId);

    List<WorkOrder> findByAssignedMechanicId(UUID mechanicId);
}
