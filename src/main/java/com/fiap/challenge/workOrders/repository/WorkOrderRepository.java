package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrderModel, UUID> {

    List<WorkOrderModel> findByStatus(WorkOrderStatus status);

    List<WorkOrderModel> findByCustomerId(UUID customerId);

    List<WorkOrderModel> findByAssignedMechanicId(UUID mechanicId);
}
