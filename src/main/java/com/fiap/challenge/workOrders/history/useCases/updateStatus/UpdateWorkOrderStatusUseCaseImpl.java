package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import org.springframework.stereotype.Service;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateWorkOrderStatusUseCaseImpl implements UpdateWorkOrderStatusUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderHistoryRepository workOrderHistoryRepository;

    @Override
    public WorkOrderModel execute(UpdateWorkOrderStatusCommand command) {
        WorkOrderModel workOrder = workOrderRepository.findById(command.workOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Work Order not found: " + command.workOrderId()));

        if (workOrder.getStatus().equals(command.newStatus())) {
            System.out.println("Status is already " + command.newStatus() + ". No action taken.");
            return workOrder;
        }

        workOrder.setStatus(command.newStatus());

        WorkOrderHistoryModel historyEntry = WorkOrderHistoryModel.builder()
                .workOrder(workOrder)
                .status(command.newStatus())
                .notes(command.notes())
                .build();

        workOrderHistoryRepository.save(historyEntry);

        return workOrder;
    }
}