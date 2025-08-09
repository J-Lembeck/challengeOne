package com.fiap.challenge.workOrders.repository;

import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import org.springframework.data.jpa.domain.Specification;

public class WorkOrderSpecification {

    public static Specification<WorkOrderModel> list(WorkOrderFilterDTO filterDTO) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (filterDTO.getStatus() != null) {
                predicates.getExpressions().add(cb.equal(root.get("status"), filterDTO.getStatus()));
            }

            return predicates;
        };
    }
}
