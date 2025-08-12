package com.fiap.challenge.workOrders.useCases.update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.challenge.parts.entity.PartModel;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.WorkOrderPartModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderStatusUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import com.fiap.challenge.parts.useCases.update.ReturnPartsToStockUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AceptedOrRefuseWorkOrderUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private UpdateWorkOrderStatusUseCase updateWorkOrderStatusUseCase;

    @Mock private AvarageTimeWorkOrderUseCase avarageTimeWorkOrderUseCase;

    @Mock
    private ReturnPartsToStockUseCase returnPartsToStockUseCase;

    @InjectMocks
    private AceptedOrRefuseWorkOrderUseCaseImpl useCase;

    private UUID workOrderId;
    private WorkOrderModel workOrder;

    @BeforeEach
    void setup() {
        workOrderId = UUID.randomUUID();

        // Criando uma WorkOrderModel com peças para o teste de recusa
        PartModel part = new PartModel();
        part.setId(UUID.randomUUID());

        WorkOrderPartModel workOrderPart = new WorkOrderPartModel();
        workOrderPart.setPart(part);
        workOrderPart.setQuantity(5);

        workOrder = new WorkOrderModel();
        workOrder.setId(workOrderId);
        workOrder.setWorkOrderPartModels(List.of(workOrderPart));
        workOrder.setStatus(WorkOrderStatus.RECEIVED);
    }

    @Test
    void shouldSetStatusInProgressWhenAccepted() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));
        when(workOrderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        doNothing().when(avarageTimeWorkOrderUseCase).executeInit(eq(workOrderId));

        // Act
        StatusWorkOrderRespondeDTO result = useCase.execute(workOrderId, true).getData();

        // Assert
        assertEquals(workOrderId, result.id());
        assertEquals(WorkOrderStatus.IN_PROGRESS, result.status());

        assertEquals(WorkOrderStatus.IN_PROGRESS, workOrder.getStatus());

        verify(returnPartsToStockUseCase, never()).execute(any(), anyInt());
        verify(updateWorkOrderStatusUseCase).execute(any(UpdateWorkOrderStatusCommand.class));
        verify(workOrderRepository).save(workOrder);
    }

    @Test
    void shouldSetStatusRefusedAndReturnPartsToStockWhenNotAccepted() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));
        when(workOrderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StatusWorkOrderRespondeDTO result = useCase.execute(workOrderId, false).getData();

        // Assert
        assertEquals(workOrderId, result.id());
        assertEquals(WorkOrderStatus.REFUSED, result.status());

        assertEquals(WorkOrderStatus.REFUSED, workOrder.getStatus());

        // Verifica se as peças foram devolvidas ao estoque
        verify(returnPartsToStockUseCase).execute(eq(workOrder.getWorkOrderPartModels().get(0).getPart().getId()), eq(5));

        verify(updateWorkOrderStatusUseCase).execute(any(UpdateWorkOrderStatusCommand.class));
        verify(workOrderRepository).save(workOrder);
    }

    @Test
    void shouldThrowExceptionWhenWorkOrderNotFound() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(workOrderId, true);
        });
        assertTrue(exception.getMessage().contains("Work order not found"));
    }
}
