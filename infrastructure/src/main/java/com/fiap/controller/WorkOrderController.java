package com.fiap.controller;

import com.fiap.core.exception.BadRequestException;
import com.fiap.core.exception.BusinessRuleException;
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
@RequestMapping("v1/work-orders")
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

    /*@Operation(
            summary = "Vincula um mecânico a uma ordem de serviço",
            description = "Endpoint para vincular um mecânico a uma ordem de serviço pelo ID da OS e do Mecânico")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Mecânico vinculado com sucesso.") })
    @PatchMapping("/{id}/assign-mechanic")
    public ResponseEntity<ResponseApi<AssignedMechanicResponseDTO>> assignMechanic(@PathVariable UUID id, @RequestBody InputAssignMechanicDTO inputAssignMechanicDTO) {
        ResponseApi<AssignedMechanicResponseDTO> responseApi = assignedMechanicUseCase.execute(id, inputAssignMechanicDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    /*@Operation(
            summary = "Altera o status de uma ordem de serviço",
            description = "Endpoint para alterar o status de uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Status alterado com sucesso.") })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = updateStatusWorkOrderUseCase.execute(id, status);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    /*@Operation(
            summary = "Aceita ou recusa uma ordem de serviço",
            description = "Endpoint para aceitar ou recusar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço aceita ou recusada com sucesso.") })
    @PatchMapping("/{id}/decision")
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> aceptedOrRefuse(@PathVariable UUID id, @RequestBody boolean decision) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = aceptedOrRefuseWorkOrderUseCase.execute(id, decision);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    /*@PatchMapping("/{id}/delivered")
    @Operation(
            summary = "Marca uma ordem de serviço como entregue",
            description = "Endpoint para marcar uma ordem de serviço como entregue pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço marcada como entregue com sucesso.") })
    public ResponseEntity<ResponseApi<StatusWorkOrderRespondeDTO>> markAsDelivered(@PathVariable UUID id) {
        ResponseApi<StatusWorkOrderRespondeDTO> responseApi = updateStatusWorkOrderUseCase.execute(id, "DELIVERED");
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/


    /*@Operation(
            summary = "Finaliza uma ordem de serviço",
            description = "Endpoint para finalizar uma ordem de serviço pelo ID")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ordem de serviço finalizada com sucesso.") })
    @PatchMapping("/{id}/finalize")
    public ResponseEntity<ResponseApi<Void>> finalizeWorkOrder(@PathVariable UUID id) {
        ResponseApi<Void> responseApi = finalizeWorkOrderUseCase.execute(id);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

    /*@Operation(
            summary = "Adiciona novos itens para a Ordem de Serviço",
            description = "Endpoint para adicionar novas peças/insumos para a ordem de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Itens adicionados com sucesso.") })
    @PatchMapping("/{id}/update-items")
    public ResponseEntity<ResponseApi<WorkOrderResumeDTO>> updateItems(@PathVariable UUID id, @RequestBody WorkOrderItemDTO workOrderItemDTO) {
        ResponseApi<WorkOrderResumeDTO> responseApi = updateWorkOrderItemsUseCase.execute(id, workOrderItemDTO);
        return ResponseEntity.status(responseApi.getStatus()).body(responseApi);
    }*/

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

    /*@Operation(
            summary = "Calcula o tempo médio de conclusão das ordens de serviço",
            description = "Endpoint para calcular o tempo médio de conclusão das ordens de serviço")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Tempo médio calculado com sucesso.") })
    @GetMapping("/calculate-avarage-time")
    public ResponseEntity<String> calculateAvarageTime() {
        ResponseApi<List<WorkOrderAvarageTime>> responseApi = findAvarageTimeWorkOrderUseCase.executeList();
        HttpStatus status = HttpStatus.valueOf(responseApi.getStatus().name());
        if (responseApi.getStatus().is4xxClientError()) return ResponseEntity.status(status).body(responseApi.getMessage());
        List<WorkOrderAvarageTime> allAvarageTimes  = responseApi.getData();
        Duration avarageTimeMessage = allAvarageTimes.stream()
                .map(WorkOrderAvarageTime::avarageTime)
                .reduce(Duration.ZERO, Duration::plus)
                .dividedBy(allAvarageTimes.size());

        return ResponseEntity.ok(
                String.format("%02d:%02d",
                        avarageTimeMessage.toHoursPart(),
                        avarageTimeMessage.toMinutesPart())
        );
    }*/
}
