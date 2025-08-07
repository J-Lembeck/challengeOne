package com.fiap.challenge.workOrders.repoisitory;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderModel, UUID> {

    @Query("SELECT wo FROM WorkOrderModel wo WHERE wo.id = :id AND wo.status = :status")
    Optional<WorkOrderModel> findByIdAndStatus(@Param("id") UUID id, @Param("status") String status);
}