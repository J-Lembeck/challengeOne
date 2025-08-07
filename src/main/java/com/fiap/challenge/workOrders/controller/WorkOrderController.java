package com.fiap.challenge.workOrders.controller;

import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.useCases.update.AssignedMechanicUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/work-orders")
public class WorkOrderController {

    private final AssignedMechanicUseCase assignedMechanicUseCase;

    public WorkOrderController(AssignedMechanicUseCase assignedMechanicUseCase) {
        this.assignedMechanicUseCase = assignedMechanicUseCase;
    }

    @PutMapping("/{workOrderId}/assigned-mechanic")
    public ResponseEntity<AssignedMechanicResponseDTO> assignMechanic(@PathVariable UUID workOrderId, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        return ResponseEntity.ok(assignedMechanicUseCase.execute(workOrderId, inputAssignMechanicDTO));
    }
}
