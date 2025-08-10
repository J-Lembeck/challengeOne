package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.parts.useCases.update.ReturnPartsToStockUseCase;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderStatusUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AceptedOrRefuseWorkOrderUseCaseImpl implements AceptedOrRefuseWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final UpdateWorkOrderStatusUseCase updateWorkOrderStatusUseCase;
    private final ReturnPartsToStockUseCase returnPartsToStockUseCase;

    @Transactional
    @Override
    public StatusWorkOrderRespondeDTO execute(UUID workOrderId, boolean accepted) {
        var workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Work order not found with id: " + workOrderId));

        if (!accepted) {
            workOrder.setStatus(WorkOrderStatus.REFUSED);
            workOrder.getWorkOrderPartModels().forEach(workOrderPart -> {
                var partId = workOrderPart.getPart().getId();
                var quantity = workOrderPart.getQuantity();
                returnPartsToStockUseCase.execute(partId, quantity);
            });
        }else {
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }
        WorkOrderModel workOrderModel= workOrderRepository.save(workOrder);

        updateWorkOrderStatusUseCase.execute(new UpdateWorkOrderStatusCommand(workOrder.getId(), workOrderModel.getStatus()));

        return new StatusWorkOrderRespondeDTO(
            workOrderModel.getId(),
            workOrderModel.getStatus()
        );
    }
}
