package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderModel, UUID>, JpaSpecificationExecutor<WorkOrderModel> {

    List<WorkOrderModel> findByStatus(WorkOrderStatus status);

    List<WorkOrderModel> findByCustomerId(UUID customerId);

    List<WorkOrderModel> findByAssignedMechanicId(UUID mechanicId);
}
