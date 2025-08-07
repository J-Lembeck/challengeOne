package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotFoundException;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.repoisitory.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindWorkOrderByIdUseCaseImpl implements FindWorkOrderByIdUseCase {

    private final WorkOrderRepository workOrderRepository;

    @Override
    public WorkOrderModel execute(UUID workOrderId) {
        return workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new WorkOrderNotFoundException(workOrderId));

    }
}
