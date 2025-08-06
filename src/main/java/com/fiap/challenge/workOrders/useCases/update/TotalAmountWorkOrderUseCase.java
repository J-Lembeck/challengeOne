package com.fiap.challenge.workOrders.useCases.update;

import java.math.BigDecimal;
import java.util.UUID;

public interface TotalAmountWorkOrderUseCase {
    BigDecimal execute(UUID id);
}
