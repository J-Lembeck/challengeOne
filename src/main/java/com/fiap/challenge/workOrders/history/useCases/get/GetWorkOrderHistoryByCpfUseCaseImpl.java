package com.fiap.challenge.workOrders.history.useCases.get;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.WorkOrderHistoryItemDTO;
import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetWorkOrderHistoryByCpfUseCaseImpl implements GetWorkOrderHistoryByCpfUseCase {

    private final CustomerRepository customerRepository;
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderHistoryRepository workOrderHistoryRepository;

    public List<WorkOrderWithHistoryResponseDTO> execute(String cpfCnpj) {
        CustomerModel customer = customerRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o CPF: " + cpfCnpj));

        List<WorkOrderModel> workOrders = workOrderRepository.findByCustomerOrderByCreatedAtDesc(customer);

        if (workOrders.isEmpty()) {
            return new ArrayList<>();
        }

        return workOrders.stream()
                .map(this::createWorkOrderResponseDTO)
                .toList();
    }

    private WorkOrderWithHistoryResponseDTO createWorkOrderResponseDTO(WorkOrderModel workOrder) {
        List<WorkOrderHistoryModel> historyEntries = workOrderHistoryRepository.findByWorkOrderOrderByCreatedAtAsc(workOrder);

        List<WorkOrderHistoryItemDTO> historyItems = historyEntries.stream()
                .map(this::mapToHistoryItemDTO)
                .toList();

        return new WorkOrderWithHistoryResponseDTO(
                workOrder.getId(),
                workOrder.getTotalAmount(),
                historyItems
        );
    }

    private WorkOrderHistoryItemDTO mapToHistoryItemDTO(WorkOrderHistoryModel historyEntry) {
        return new WorkOrderHistoryItemDTO(
                historyEntry.getStatus().getDescription(),
                historyEntry.getNotes(),
                historyEntry.getCreatedAt()
        );
    }
}