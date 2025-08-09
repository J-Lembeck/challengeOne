package com.fiap.challenge.workOrders.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;

public interface WorkOrderRepository extends JpaRepository<WorkOrderModel, UUID> {

    List<WorkOrderModel> findByStatus(WorkOrderStatus status);

    List<WorkOrderModel> findByCustomerId(UUID customerId);

    List<WorkOrderModel> findByAssignedMechanicId(UUID mechanicId);

	List<WorkOrderModel> findByCustomerOrderByCreatedAtDesc(CustomerModel customer);
}
