package com.fiap.challenge.workOrders.controller;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.workOrders.dto.*;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.useCases.CreateWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.GetWorkOrderByIdUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrdersByFilterUseCase;
import org.springframework.http.HttpStatus;
import com.fiap.challenge.workOrders.useCases.update.AceptedOrRefuseWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.update.UpdateStatusWorkOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final GetWorkOrderByIdUseCase getWorkOrderByIdUseCase;
    private final FindWorkOrdersByFilterUseCase findWorkOrdersByFilterUseCase;

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
    @PutMapping("/{id}/assign-mechanic")
    public ResponseEntity<AssignedMechanicResponseDTO> assignMechanic(@PathVariable UUID id, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        return ResponseEntity.ok(assignedMechanicUseCase.execute(id, inputAssignMechanicDTO));
    }
  
  @PostMapping
    public ResponseEntity<WorkOrderResponseDTO> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        WorkOrderModel created = createWorkOrderUseCase.execute(dto);

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
        var workOrderModel = getWorkOrderByIdUseCase.execute(id);

        var items = workOrderModel.getItems().stream()
                .map(item -> new WorkOrderItemDTO(
                        item.getPart() != null ? item.getPart().getId() : null,
                        item.getService() != null ? item.getService().getId() : null,
                        item.getQuantity(),
                        item.getPrice()
                )).toList();

        WorkOrderResponseDTO response = new WorkOrderResponseDTO(
                workOrderModel.getId(),
                workOrderModel.getCustomer().getId(),
                workOrderModel.getVehicle().getId(),
                workOrderModel.getCreatedBy().getId(),
                workOrderModel.getAssignedMechanic() != null ? workOrderModel.getAssignedMechanic().getId() : null,
                workOrderModel.getTotalAmount(),
                items
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<WorkOrderResumeDTO>> getWorkOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "dataCriacao") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();
        filter.setStatus(WorkOrderStatus.fromString(status));

        List<WorkOrderResumeDTO> response = findWorkOrdersByFilterUseCase.execute(filter);

        return ResponseEntity.ok(response);
    }
}
