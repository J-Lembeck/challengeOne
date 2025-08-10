package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderPartUseCase;
import com.fiap.challenge.workOrders.useCases.create.CreateWorkOrderServiceUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateWorkOrderItemsUseCaseImpl implements  UpdateWorkOrderItemsUseCase{

    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderUseCase updateWorkOrderUseCase;
    private final CreateWorkOrderPartUseCase createWorkOrderPartUseCase;
    private final CreateWorkOrderServiceUseCase createWorkOrderServiceUseCase;
    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;
    private final WorkOrderMapper workOrderMapper;

    @Override
    @Transactional
    public WorkOrderResumeDTO execute(UUID id, WorkOrderItemDTO workOrderItemDTO) {

        var workOrder = findWorkOrderByIdUseCase.execute(id);

        createWorkOrderPartUseCase.execute(workOrder, workOrderItemDTO.parts());
        createWorkOrderServiceUseCase.execute(workOrder,workOrderItemDTO.services());

        workOrder.recalculateTotal(); // Agora a soma é responsabilidade da entidade
        updateStatusWorkOrderUseCase.execute(workOrder.getId(), WorkOrderStatus.AWAITING_APPROVAL.name());

        updateWorkOrderUseCase.execute(workOrder);

        return workOrderMapper.toWorkOrderResumeDTO(workOrder);
    }
}
