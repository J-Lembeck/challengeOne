package com.fiap.challenge.workOrders.history.useCases.get;

import java.util.List;

import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;

public interface GetWorkOrderHistoryByCpfUseCase {

	ResponseApi<List<WorkOrderWithHistoryResponseDTO>> execute(String cpf);
}
