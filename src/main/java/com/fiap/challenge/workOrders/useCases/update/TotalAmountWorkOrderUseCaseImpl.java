package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.parts.entity.WorkOrderPart;
import com.fiap.challenge.parts.useCases.others.CalculatePartsValueInWorkOrderUseCase;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TotalAmountWorkOrderUseCaseImpl implements TotalAmountWorkOrderUseCase {

    private final CalculatePartsValueInWorkOrderUseCase calculatePartsValueInWorkOrderUseCase;
    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;

    @Override
    public BigDecimal execute(UUID id) {
    var workOrder = findWorkOrderByIdUseCase.execute(id);
        return calculatePartsValueInWorkOrderUseCase.execute(workOrder.getUsedParts().stream().map(WorkOrderPart::getId).toList());
    }
}
