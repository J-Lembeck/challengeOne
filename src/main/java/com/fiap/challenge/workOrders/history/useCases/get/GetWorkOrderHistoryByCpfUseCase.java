package com.fiap.challenge.workOrders.history.useCases.get;

import java.util.List;

import com.fiap.challenge.workOrders.history.dto.WorkOrderWithHistoryResponseDTO;

public interface GetWorkOrderHistoryByCpfUseCase {

	public List<WorkOrderWithHistoryResponseDTO> execute(String cpf);
}
