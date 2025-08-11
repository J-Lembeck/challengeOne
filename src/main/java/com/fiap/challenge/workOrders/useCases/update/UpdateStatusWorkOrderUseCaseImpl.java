package com.fiap.challenge.workOrders.useCases.update;

import java.util.UUID;

import com.fiap.challenge.shared.model.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderStatusUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateStatusWorkOrderUseCaseImpl implements UpdateStatusWorkOrderUseCase{

    private final WorkOrderRepository workOrderRepository;
    private final UpdateWorkOrderStatusUseCase updateWorkOrderStatusUseCase;

    @Override
    public ResponseApi<StatusWorkOrderRespondeDTO> execute(UUID id, String status) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = new ResponseApi<>();
        var workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Work order not found with id: " + id));

        WorkOrderModel updatedWorkOrder;
        try {
            workOrder.setStatus(WorkOrderStatus.fromString(status));
            updatedWorkOrder = workOrderRepository.save(workOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        updateWorkOrderStatusUseCase.execute(new UpdateWorkOrderStatusCommand(workOrder.getId(), updatedWorkOrder.getStatus()));

        return responseApi.of(HttpStatus.OK, "Work order status updated successfully!",
                new StatusWorkOrderRespondeDTO(updatedWorkOrder.getId(), updatedWorkOrder.getStatus()));
    }
}
