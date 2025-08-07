package com.fiap.challenge.parts.useCases.others;

import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.parts.repository.WorkOrderPartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculatePartsValueInWorkOrderUseCaseImpl implements CalculatePartsValueInWorkOrderUseCase {

    private final WorkOrderPartRepository workOrderPartRepository;

    @Override
    public BigDecimal execute(List<UUID> ids) {
        return workOrderPartRepository.findAllById(ids)
            .stream()
            .map(workOrderPart -> workOrderPart.getUnitPrice()
                    .multiply(BigDecimal.valueOf(workOrderPart.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
