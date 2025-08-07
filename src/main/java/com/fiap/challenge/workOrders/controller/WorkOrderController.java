package com.fiap.challenge.workOrders.controller;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.useCases.update.AceptedOrRefuseWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.update.UpdateStatusWorkOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.useCases.update.AssignedMechanicUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "Ordem de Serviço", description = "Controlador para ordem de serviço")
public class WorkOrderController {

    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final AceptedOrRefuseWorkOrderUseCase aceptedOrRefuseWorkOrderUseCase;
    private final AssignedMechanicUseCase assignedMechanicUseCase;

    @Operation(
        summary = "Altera o status de uma ordem de serviço",
        description = "Endpoint para alterar o status de uma ordem de serviço pelo ID")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Status alterado com sucesso.") })
    @PatchMapping("/{id}/status")
    public ResponseEntity<StatusWorkOrderRespondeDTO> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        return ResponseEntity.ok(updateStatusWorkOrderUseCase.execute(id, status));
    }

    @Operation(
        summary = "Aceita ou recusa uma ordem de serviço",
        description = "Endpoint para aceitar ou recusar uma ordem de serviço pelo ID")
    @ApiResponses(
        value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço aceita ou recusada com sucesso.") })
    @PatchMapping("/{id}/decision")
    public ResponseEntity<StatusWorkOrderRespondeDTO> aceptedOrRefuse(@PathVariable UUID id, @RequestBody boolean decision) {
        return ResponseEntity.ok(aceptedOrRefuseWorkOrderUseCase.execute(id, decision));
    }

    @Operation(
            summary = "Vincula um mecânico a uma ordem de serviço",
            description = "Endpoint para vincular um mecânico a uma ordem de serviço pelo ID da OS e do Mecânico")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Mecânico vinculado com sucesso.") })
    @PutMapping("/{workOrderId}/assigned-mechanic")
    public ResponseEntity<AssignedMechanicResponseDTO> assignMechanic(@PathVariable UUID workOrderId, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        return ResponseEntity.ok(assignedMechanicUseCase.execute(workOrderId, inputAssignMechanicDTO));
    }

}
