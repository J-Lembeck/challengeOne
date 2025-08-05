package com.fiap.challenge.workOrders.controller;

import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.useCases.update.UpdateStatusWorkOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final UpdateStatusWorkOrderUseCase updateStatusWorkOrderUseCase;

    @PatchMapping("/{id}/status")
    public ResponseEntity<StatusWorkOrderRespondeDTO> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        return ResponseEntity.ok(updateStatusWorkOrderUseCase.execute(id, status));
    }

}
