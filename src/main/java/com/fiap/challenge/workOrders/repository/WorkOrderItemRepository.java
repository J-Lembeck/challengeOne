package com.fiap.challenge.workOrders.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fiap.challenge.workOrders.entity.WorkOrderItem;

@Repository
public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, UUID> {
    boolean existsByPartId(UUID partId);
}
