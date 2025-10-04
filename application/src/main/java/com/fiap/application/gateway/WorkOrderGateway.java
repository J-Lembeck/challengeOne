package com.fiap.application.gateway;

import com.fiap.core.domain.WorkOrder;
import com.fiap.core.exception.DocumentNumberException;

import java.util.Optional;
import java.util.UUID;

public interface WorkOrderGateway {

    Optional<WorkOrder> findById(UUID customerId) throws DocumentNumberException;
}
