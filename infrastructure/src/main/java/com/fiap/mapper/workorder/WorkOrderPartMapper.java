package com.fiap.mapper.workorder;

import com.fiap.core.domain.workorder.WorkOrderPart;
import com.fiap.dto.workorder.WorkOrderPartRequest;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderPartMapper {

    public WorkOrderPart toDomain(WorkOrderPartRequest request) {
        return new WorkOrderPart(
                request.partId(),
                request.quantity()
        );
    }
}
