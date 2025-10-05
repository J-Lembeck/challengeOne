package com.fiap.dto.workorder;

import java.util.List;
import java.util.UUID;

public record CreateWorkOrderRequest(
        UUID customerId,
        UUID vehicleId,
        UUID createdById,
        UUID assignedMechanicId,
        List<WorkOrderPartRequest> parts,
        List<WorkOrderServiceRequest> services
        ){

}
