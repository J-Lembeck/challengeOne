package com.fiap.challenge.workOrders.controller;

import java.util.List;
import java.util.UUID;

import com.fiap.challenge.workOrders.dto.*;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderPartUseCase;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrdersByFilterUseCase;
import com.fiap.challenge.workOrders.useCases.update.UpdateWorkOrderItemsUseCase;
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

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
@Tag(name = "Ordem de Serviço", description = "Controlador para ordem de serviço")
public class WorkOrderController {

    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final AceptedOrRefuseWorkOrderUseCase aceptedOrRefuseWorkOrderUseCase;
    private final AssignedMechanicUseCase assignedMechanicUseCase;
    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final FindWorkOrdersByFilterUseCase findWorkOrdersByFilterUseCase;
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderItemsUseCase updateWorkOrderItemsUseCase;

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
    @PatchMapping("/{id}/assign-mechanic")
    public ResponseEntity<AssignedMechanicResponseDTO> assignMechanic(@PathVariable UUID id, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        return ResponseEntity.ok(assignedMechanicUseCase.execute(id, inputAssignMechanicDTO));
    }

    @Operation(
            summary = "Cria uma ordem de serviço",
            description = "Endpoint para criar uma ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso.") })
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
                dto.parts(),
                dto.services()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Retorna uma ordem de serviço pelo ID",
            description = "Endpoint para retornar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço encontrada com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponseDTO> getWorkOrderById(@PathVariable UUID id) {
        var workOrderDTO = findWorkOrderByIdUseCase.executeToDTO(id);

        return ResponseEntity.ok(workOrderDTO);
    }

    @Operation(
            summary = "Retorna Lista de Ordem de Serviço",
            description = "Endpoint para retornar OS, podendo filtrar pelo status")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordens de Serviço retornadas com sucesso.") })
    @GetMapping("/list")
    public ResponseEntity<List<WorkOrderResumeDTO>> getWorkOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();

        System.out.println("aqui");
        if (status != null) filter.setStatus(WorkOrderStatus.fromString(status));
        System.out.println("aquiagr");

        List<WorkOrderResumeDTO> response = findWorkOrdersByFilterUseCase.execute(filter);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Adiciona novos itens para a Ordem de Serviço",
            description = "Endpoint para adicionar novas peças/insumos para a ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Itens adicionados com sucesso.") })
    @PatchMapping("/{id}/update-items")
    public ResponseEntity<WorkOrderResumeDTO> updateItems(
            @PathVariable UUID id,
            @RequestBody WorkOrderItemDTO workOrderItemDTO
    ) {

        WorkOrderResumeDTO response = updateWorkOrderItemsUseCase.execute(id, workOrderItemDTO);

        return ResponseEntity.ok(response);
    }
}
