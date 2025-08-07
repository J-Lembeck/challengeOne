package com.fiap.challenge.workOrders.useCases;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetWorkOrderByIdUseCase {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderModel execute(UUID id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
    }
}
