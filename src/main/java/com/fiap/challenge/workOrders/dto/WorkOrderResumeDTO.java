package com.fiap.challenge.workOrders.dto;

import com.fiap.challenge.customers.dto.CustomerResumeDTO;
import com.fiap.challenge.vehicles.dto.VehicleResumeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderResumeDTO {

    UUID workOrderId;
    CustomerResumeDTO customerResume;
    VehicleResumeDTO vehicleResume;
    String createdBy;
    String assignedMechanic;
}
