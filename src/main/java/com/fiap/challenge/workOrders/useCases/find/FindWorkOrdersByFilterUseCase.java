package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;

import java.util.List;

public interface FindWorkOrdersByFilterUseCase {

    List<WorkOrderResumeDTO> execute(WorkOrderFilterDTO filterDTO);
}
