package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fiap.challenge.workOrders.dto.StatusWorkOrderRespondeDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.useCases.updateStatus.UpdateWorkOrderStatusUseCase;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateStatusWorkOrderUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private UpdateWorkOrderStatusUseCase updateWorkOrderStatusUseCase;

    @InjectMocks
    private UpdateStatusWorkOrderUseCaseImpl useCase;

    private UUID workOrderId;
    private WorkOrderModel existingWorkOrder;

    @BeforeEach
    void setUp() {

        workOrderId = UUID.randomUUID();

        existingWorkOrder = WorkOrderModel.builder()
                .id(workOrderId)
                .status(WorkOrderStatus.COMPLETED)
                .build();
    }

    @Test
    void shouldUpdateStatusSuccessfully() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(existingWorkOrder));
        when(workOrderRepository.save(any(WorkOrderModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StatusWorkOrderRespondeDTO response = useCase.execute(workOrderId, "COMPLETED").getData();

        // Assert
        assertNotNull(response);
        assertEquals(workOrderId, response.id());
        assertEquals(WorkOrderStatus.COMPLETED, response.status());

        verify(workOrderRepository).findById(workOrderId);
        verify(workOrderRepository).save(existingWorkOrder);
        verify(updateWorkOrderStatusUseCase).execute(any(UpdateWorkOrderStatusCommand.class));
    }

    @Test
    void shouldThrowExceptionWhenWorkOrderNotFound() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(workOrderId, "CLOSED"));
        assertTrue(exception.getMessage().contains("Work order not found"));

        verify(workOrderRepository).findById(workOrderId);
        verify(workOrderRepository, never()).save(any());
        verifyNoInteractions(updateWorkOrderStatusUseCase);
    }

    @Test
    void shouldThrowExceptionWhenStatusIsInvalid() {
        // Arrange
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(existingWorkOrder));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(workOrderId, "INVALID_STATUS"));
        assertTrue(exception.getMessage().contains("Invalid status"));

        verify(workOrderRepository).findById(workOrderId);
        verify(workOrderRepository, never()).save(any());
        verifyNoInteractions(updateWorkOrderStatusUseCase);
    }

    @Test
    void executeWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> { WorkOrderStatus.fromString("PENDING");
        });
    }

    @Test
    void executeWithValidStatus() {
        WorkOrderStatus status = WorkOrderStatus.fromString("IN_PROGRESS");
        assertEquals(WorkOrderStatus.IN_PROGRESS, status);
    }
}
