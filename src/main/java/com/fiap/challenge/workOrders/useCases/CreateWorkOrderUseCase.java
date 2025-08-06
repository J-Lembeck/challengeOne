package com.fiap.challenge.workOrders.useCases;

import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.vehicles.repository.VehicleRepository;
import com.fiap.challenge.users.repository.UserRepository;
import com.fiap.challenge.parts.repository.PartsRepository;
import com.fiap.challenge.services.repository.ServiceRepository;
import com.fiap.challenge.workOrders.dto.WorkOrderDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderItemDTO;
import com.fiap.challenge.workOrders.entity.WorkOrder;
import com.fiap.challenge.workOrders.entity.WorkOrderItem;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final PartsRepository partsRepository;
    private final ServiceRepository serviceRepository;

    public WorkOrder execute(WorkOrderDTO dto) {

        var customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        var vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        var createdBy = userRepository.findById(dto.createdById())
                .orElseThrow(() -> new EntityNotFoundException("Usuário criador não encontrado"));

        var mechanic = dto.assignedMechanicId() != null
                ? userRepository.findById(dto.assignedMechanicId())
                .orElseThrow(() -> new EntityNotFoundException("Mecânico atribuído não encontrado"))
                : null;

        // Cria a OS com status inicial correto
        WorkOrder workOrder = WorkOrder.builder()
                .customer(customer)
                .vehicle(vehicle)
                .createdBy(createdBy)
                .assignedMechanic(mechanic)
                .status(WorkOrderStatus.RECEIVED) // Corrigido: status válido
                .build();

        // Monta os itens
        List<WorkOrderItem> items = new ArrayList<>();
        for (WorkOrderItemDTO itemDTO : dto.items()) {
            items.add(buildWorkOrderItem(itemDTO, workOrder));
        }

        workOrder.setItems(items);
        workOrder.recalculateTotal(); // Agora a soma é responsabilidade da entidade

        return workOrderRepository.save(workOrder);
    }

    private WorkOrderItem buildWorkOrderItem(WorkOrderItemDTO dto, WorkOrder workOrder) {
        var part = dto.partId() != null
                ? partsRepository.findById(dto.partId())
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada"))
                : null;

        var service = dto.serviceId() != null
                ? serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"))
                : null;

        if (part == null && service == null) {
            throw new IllegalArgumentException("Cada item deve ter ao menos uma peça ou serviço associado");
        }

        return WorkOrderItem.builder()
                .workOrder(workOrder)
                .part(part)
                .service(service)
                .quantity(dto.quantity())
                .price(dto.price())
                .build();
    }
}
