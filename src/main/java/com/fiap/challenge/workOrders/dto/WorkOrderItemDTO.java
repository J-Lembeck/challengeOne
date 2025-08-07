package com.fiap.challenge.workOrders.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WorkOrderItemDTO(
        UUID partId,
        UUID serviceId,
        Integer quantity,
        BigDecimal price
) {}
