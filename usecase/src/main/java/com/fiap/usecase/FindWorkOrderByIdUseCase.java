package com.fiap.usecase;

import com.fiap.core.domain.WorkOrder;

import java.util.UUID;

public interface FindWorkOrderByIdUseCase {

    WorkOrder execute(UUID id);
}
