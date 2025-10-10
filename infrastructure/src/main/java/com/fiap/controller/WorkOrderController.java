package com.fiap.controller;

import com.fiap.core.exception.BadRequestException;
import com.fiap.core.exception.BusinessRuleException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.dto.workorder.*;
import com.fiap.mapper.workorder.WorkOrderMapper;
import com.fiap.usecase.workorder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("v1/work-orders")
public class WorkOrderController {

    private final CreateWorkOrderUseCase createWorkOrderUseCase;
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final AssignedMechanicUseCase assignedMechanicUseCase;
    private final WorkOrderMapper workOrderMapper;
    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final GetWorkOrderStatusUseCase getWorkOrderStatusUseCase;
    private final ApproveWorkOrderUseCase approveWorkOrderUseCase;
    private final RefuseWorkOrderUseCase refuseWorkOrderUseCase;
    private final AddItemsWorkOrderUseCase addItemsWorkOrderUseCase;
    private final CalculateAverageTimeWorkOrderUseCase calculateAverageTimeWorkOrderUseCase;

    public WorkOrderController(CreateWorkOrderUseCase createWorkOrderUseCase, FindWorkOrderByIdUseCase findWorkOrderByIdUseCase, AssignedMechanicUseCase assignedMechanicUseCase, WorkOrderMapper workOrderMapper, UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase, GetWorkOrderStatusUseCase getWorkOrderStatusUseCase, ApproveWorkOrderUseCase approveWorkOrderUseCase, RefuseWorkOrderUseCase refuseWorkOrderUseCase, AddItemsWorkOrderUseCase addItemsWorkOrderUseCase, CalculateAverageTimeWorkOrderUseCase calculateAverageTimeWorkOrderUseCase) {
        this.createWorkOrderUseCase = createWorkOrderUseCase;
        this.findWorkOrderByIdUseCase = findWorkOrderByIdUseCase;
        this.assignedMechanicUseCase = assignedMechanicUseCase;
        this.workOrderMapper = workOrderMapper;
        this.updateStatusWorkOrderUseCase = updateStatusWorkOrderUseCase;
        this.getWorkOrderStatusUseCase = getWorkOrderStatusUseCase;
        this.approveWorkOrderUseCase = approveWorkOrderUseCase;
        this.refuseWorkOrderUseCase = refuseWorkOrderUseCase;
        this.addItemsWorkOrderUseCase = addItemsWorkOrderUseCase;
        this.calculateAverageTimeWorkOrderUseCase = calculateAverageTimeWorkOrderUseCase;
    }

    @Operation(
            summary = "Cria uma nova ordem de serviço",
            description = "Endpoint para criar uma nova ordem de serviço.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso.") })
    @PostMapping("/create")
    public ResponseEntity<WorkOrderResponse> createWorkOrder(@RequestBody CreateWorkOrderRequest request) throws NotFoundException, BadRequestException, BusinessRuleException {
        var workOrder = createWorkOrderUseCase.execute(workOrderMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderMapper.toResponse(workOrder));
    }

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
            summary = "Retorna Lista de Ordem de Serviço",
            description = "Endpoint para retornar OS, podendo filtrar pelo status")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordens de Serviço retornadas com sucesso.") })
    @GetMapping("/list")
    public ResponseEntity<ResponseApi<List<WorkOrderResumeDTO>>> getWorkOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        WorkOrderFilterDTO filter = new WorkOrderFilterDTO();

        if (status != null) filter.setStatus(WorkOrderStatus.fromString(status));

        ResponseApi<List<WorkOrderResumeDTO>> responseApi = findWorkOrdersByFilterUseCase.execute(filter);

        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    @Operation(
            summary = "Vincula um mecânico a uma ordem de serviço",
            description = "Endpoint para vincular um mecânico a uma ordem de serviço pelo ID da OS e do Mecânico")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Mecânico vinculado com sucesso.") })
    @PatchMapping("/{id}/assign-mechanic")
    public ResponseEntity<String> assignMechanic(@PathVariable UUID id, @RequestBody WorkOrderAssignMechanicRequest assignMechanicRequest) throws NotFoundException, BadRequestException {
        assignedMechanicUseCase.execute(id, assignMechanicRequest.mechanicId());
        return ResponseEntity.ok("Mecânico vinculado.");
    }

    @Operation(
            summary = "Atualiza o status de uma ordem de serviço",
            description = "Endpoint para alterar o status de uma ordem de serviço pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço não encontrada."),
            @ApiResponse(responseCode = "400", description = "Status inválido informado.")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<WorkOrderResponse> updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateStatusWorkOrderRequest request
    ) throws NotFoundException, BadRequestException, BusinessRuleException {
        var workOrder = updateStatusWorkOrderUseCase.execute(id, request.status());
        return ResponseEntity.ok(workOrderMapper.toResponse(workOrder));
    }

    @Operation(
            summary = "Consulta o status da ordem de serviço pelo ID",
            description = "Endpoint para consultar apenas o status atual de uma ordem de serviço.")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Status da ordem de serviço obtido com sucesso.") })
    @GetMapping("/{id}/status")
    public ResponseEntity<WorkOrderStatusResponse> getWorkOrderStatus(@PathVariable UUID id) throws NotFoundException {
        var status = getWorkOrderStatusUseCase.execute(id);
        return ResponseEntity.ok().body(new WorkOrderStatusResponse(id, status.getDescription()));
    }

    @Operation(
            summary = "Aprova uma ordem de serviço",
            description = "Endpoint para aprovar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço aprovada com sucesso.") })
    @PatchMapping("/{id}/approve")
    public ResponseEntity<String> approveWorkOrder(@PathVariable UUID id) throws NotFoundException, BadRequestException {
        approveWorkOrderUseCase.execute(id);
        return ResponseEntity.ok("Ordem de Serviço aprovada.");
    }

    @Operation(
            summary = "Reprova uma ordem de serviço",
            description = "Endpoint para reprovar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço recusada com sucesso.") })
    @PatchMapping("/{id}/refuse")
    public ResponseEntity<String> refuseWorkOrder(@PathVariable UUID id) throws NotFoundException, BadRequestException, BusinessRuleException {
        refuseWorkOrderUseCase.execute(id);
        return ResponseEntity.ok("Ordem de Serviço recusada.");
    }

    @Operation(
            summary = "Adiciona novos itens para a Ordem de Serviço",
            description = "Endpoint para adicionar novas peças/insumos para a ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Itens adicionados com sucesso.") })
    @PatchMapping("/{id}/update-items")
    public ResponseEntity<WorkOrderResponse> updateItems(@PathVariable UUID id, @RequestBody UpdateWorkOrderItemsRequest updateWorkOrderItemsRequest) throws BadRequestException, BusinessRuleException, NotFoundException {
        var workOrder = addItemsWorkOrderUseCase.execute(id, workOrderMapper.toDomain(updateWorkOrderItemsRequest));
        return ResponseEntity.status(HttpStatus.OK).body(workOrderMapper.toResponse(workOrder));
    }

    /*@Operation(
            summary = "Busca o histórico de ordens de serviço por CPF",
            description = "Endpoint para buscar o histórico de ordens de serviço pelo CPF do cliente")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Histórico de ordens de serviço encontrado com sucesso.") })
    @GetMapping("/cpf/{cpf}/latest-work-order-history")
    public ResponseEntity<ResponseApi<List<WorkOrderWithHistoryResponseDTO>>> getHistoryByCpf(@PathVariable String cpf) {
        ResponseApi<List<WorkOrderWithHistoryResponseDTO>> responseApi = getWorkOrderHistoryByCpfUseCase.execute(cpf);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    @Operation(
            summary = "Calcula o tempo médio de conclusão das ordens de serviço",
            description = "Endpoint para calcular o tempo médio de conclusão das ordens de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Tempo médio calculado com sucesso.") })
    @GetMapping("/calculate-average-time")
    public ResponseEntity<String> calculateAverageTime() {
        String average = calculateAverageTimeWorkOrderUseCase.execute();
        return ResponseEntity.ok(average);

    }
}
