package com.fiap.application.usecaseimpl.workorder;

import com.fiap.application.gateway.workorder.WorkOrderGateway;
import com.fiap.core.domain.workorder.WorkOrder;
import com.fiap.core.domain.workorder.WorkOrderStatus;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.workorder.ApproveWorkOrderUseCase;

import java.util.UUID;

public class ApproveWorkOrderUseCaseImpl implements ApproveWorkOrderUseCase {

    private final WorkOrderGateway workOrderGateway;

    public ApproveWorkOrderUseCaseImpl(WorkOrderGateway workOrderGateway) {
        this.workOrderGateway = workOrderGateway;
    }

    @Override
    public void execute(UUID id) throws NotFoundException {

        WorkOrder workOrder = workOrderGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.WORK0001.getMessage(), ErrorCodeEnum.WORK0001.getCode()));

        workOrder.approveStock();
        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);

        workOrderGateway.save(workOrder);
    }
}
