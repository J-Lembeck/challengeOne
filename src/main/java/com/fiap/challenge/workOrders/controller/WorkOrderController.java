package com.fiap.challenge.workOrders.controller;

import java.util.UUID;

import com.fiap.challenge.workOrders.dto.WorkOrderDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResponseDTO;
import com.fiap.challenge.workOrders.entity.WorkOrder;
import com.fiap.challenge.workOrders.useCases.CreateWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.GetWorkOrderByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workorders")
public class WorkOrderController {

    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final GetWorkOrderByIdUseCase getWorkOrderByIdUseCase;

    @PostMapping
    public ResponseEntity<WorkOrderResponseDTO> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        WorkOrder created = createWorkOrderUseCase.execute(dto);

        WorkOrderResponseDTO response = new WorkOrderResponseDTO(
                created.getId(),
                created.getCustomer().getId(),
                created.getVehicle().getId(),
                created.getCreatedBy().getId(),
                created.getAssignedMechanic() != null ? created.getAssignedMechanic().getId() : null,
                created.getTotalAmount(),
                dto.items()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponseDTO> getWorkOrderById(@PathVariable UUID id) {
        var workOrder = getWorkOrderByIdUseCase.execute(id);

        var items = workOrder.getItems().stream()
                .map(item -> new WorkOrderItemDTO(
                        item.getPart() != null ? item.getPart().getId() : null,
                        item.getService() != null ? item.getService().getId() : null,
                        item.getQuantity(),
                        item.getPrice()
                )).toList();

        WorkOrderResponseDTO response = new WorkOrderResponseDTO(
                workOrder.getId(),
                workOrder.getCustomer().getId(),
                workOrder.getVehicle().getId(),
                workOrder.getCreatedBy().getId(),
                workOrder.getAssignedMechanic() != null ? workOrder.getAssignedMechanic().getId() : null,
                workOrder.getTotalAmount(),
                items
        );

        return ResponseEntity.ok(response);
    }
}
