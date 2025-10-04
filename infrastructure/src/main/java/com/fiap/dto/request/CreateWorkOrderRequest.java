package com.fiap.dto.request;

import java.util.UUID;

public record CreateWorkOrderRequest(
        UUID customerId
        //UUID vehicleId,
        //UUID createdById,
        //UUID assignedMechanicId,
        //List<WorkOrderPartDTO> parts,
        //List<WorkOrderServiceDTO> services
        ){

}
