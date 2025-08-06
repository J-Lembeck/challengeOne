package com.fiap.challenge.parts.useCases.others;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CalculatePartsValueInWorkOrderUseCase {
    BigDecimal execute(List<UUID> ids);
}
