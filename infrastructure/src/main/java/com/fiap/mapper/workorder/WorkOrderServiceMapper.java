package com.fiap.mapper.workorder;

import com.fiap.core.domain.workorder.WorkOrderService;
import com.fiap.dto.workorder.WorkOrderServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderServiceMapper {

    public WorkOrderService toDomain(WorkOrderServiceRequest request) {
        return new WorkOrderService(
                request.serviceId(),
                request.quantity()
        );
    }
}
