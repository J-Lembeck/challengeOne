package com.fiap.challenge.workOrders.dto;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderFilterDTO {

    WorkOrderStatus status;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
