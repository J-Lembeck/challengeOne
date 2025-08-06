package com.fiap.challenge.workOrders.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.challenge.parts.entity.WorkOrderPart;

@Repository
public interface WorkOrderPartRepository extends JpaRepository<WorkOrderPart, UUID> {
    
    public boolean existsByPartId(UUID partId);
}