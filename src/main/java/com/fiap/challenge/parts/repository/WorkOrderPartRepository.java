package com.fiap.challenge.parts.repository;

import com.fiap.challenge.parts.entity.WorkOrderPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkOrderPartRepository extends JpaRepository<WorkOrderPart, UUID> {}
