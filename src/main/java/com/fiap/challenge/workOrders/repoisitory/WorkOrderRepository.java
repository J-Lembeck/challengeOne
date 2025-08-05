package com.fiap.challenge.workOrders.repoisitory;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkOrderRepository extends JpaRepository<WorkOrderModel, UUID> {
    public Optional<WorkOrderStatus> findByStatus(String status);
}
