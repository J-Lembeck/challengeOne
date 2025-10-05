package com.fiap.controller;

import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.workorder.CreateWorkOrderRequest;
import com.fiap.dto.workorder.WorkOrderResponse;
import com.fiap.mapper.workorder.WorkOrderMapper;
import com.fiap.usecase.workorder.CreateWorkOrderUseCase;
import com.fiap.usecase.workorder.FindWorkOrderByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/workOrder")
public class WorkOrderController {

    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final WorkOrderMapper workOrderMapper;

    public WorkOrderController(CreateWorkOrderUseCase createWorkOrderUseCase, FindWorkOrderByIdUseCase findWorkOrderByIdUseCase, WorkOrderMapper workOrderMapper) {
        this.createWorkOrderUseCase = createWorkOrderUseCase;
        this.findWorkOrderByIdUseCase = findWorkOrderByIdUseCase;
        this.workOrderMapper = workOrderMapper;
    }

    @Operation(
            summary = "Cria uma nova ordem de serviço",
            description = "Endpoint para criar uma nova ordem de serviço.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso.") })
    @PostMapping("/create")
    public ResponseEntity<WorkOrderResponse> createWorkOrder(@RequestBody CreateWorkOrderRequest request) throws NotFoundException {
        var workOrder = createWorkOrderUseCase.execute(workOrderMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderMapper.toResponse(workOrder));
    }

    /*@Operation(
            summary = "Atualiza um cliente existente",
            description = "Endpoint para atualizar uma ordem de serviço pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço atualizada com sucesso.") })
    @PutMapping("/{serviceId}")
    public ResponseEntity<WorkOrderResponse> updateWorkOrder(@PathVariable UUID serviceId) throws DocumentNumberException, EmailException, NotFoundException, InternalServerErrorException {
        var workOrder = findWorkOrderByIdUseCase.execute(serviceId);
        return ResponseEntity.ok().body(workOrderMapper.toResponse(workOrder));
    }*/

    @Operation(
            summary = "Busca uma ordem de serviço pelo ID",
            description = "Endpoint para buscar uma ordem de serviço pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço encontrada com sucesso.") })
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponse> findWorkOrderById(@PathVariable UUID id) throws NotFoundException {
        var workOrder = findWorkOrderByIdUseCase.execute(id);
        return ResponseEntity.ok().body(workOrderMapper.toResponse(workOrder));
    }

    /*@Operation(
            summary = "Deleta uma ordem de serviço pelo ID",
            description = "Endpoint para deletar uma ordem de serviço pelo ID.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "Ordem de serviço deletada com sucesso.") })
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable UUID serviceId) throws DocumentNumberException, NotFoundException {
        deleteWorkOrderUseCase.execute(serviceId);
        return ResponseEntity.noContent().build();
    }*/
}
