package com.fiap.repository;

import java.util.List;
import java.util.UUID;

import com.fiap.core.domain.enums.WorkOrderStatus;
import com.fiap.entity.CustomerEntity;
import com.fiap.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, UUID>, JpaSpecificationExecutor<WorkOrderEntity> {

    List<WorkOrderEntity> findByStatus(WorkOrderStatus status);

    List<WorkOrderEntity> findByCustomerId(UUID customerId);

    List<WorkOrderEntity> findByAssignedMechanicId(UUID mechanicId);

	List<WorkOrderEntity> findByCustomerOrderByCreatedAtDesc(CustomerEntity customer);
}
