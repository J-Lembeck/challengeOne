package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.repoisitory.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AceptedOrRefuseWorkOrderUseCaseImpl implements AceptedOrRefuseWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;

    @Override
    public StatusWorkOrderRespondeDTO execute(UUID workOrderId, boolean accepted) {
        var workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Work order not found with id: " + workOrderId));

        if (!accepted) {
            workOrder.setStatus(WorkOrderStatus.REFUSED);
        }else {
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }
        WorkOrderModel workOrderModel= workOrderRepository.save(workOrder);

        return new StatusWorkOrderRespondeDTO(
            workOrderModel.getId(),
            workOrderModel.getStatus()
        );
    }
}
