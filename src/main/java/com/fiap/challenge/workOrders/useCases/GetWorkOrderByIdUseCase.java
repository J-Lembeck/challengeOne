package com.fiap.challenge.workOrders.useCases;

import com.fiap.challenge.workOrders.entity.WorkOrder;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetWorkOrderByIdUseCase {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrder execute(UUID id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
    }
}
