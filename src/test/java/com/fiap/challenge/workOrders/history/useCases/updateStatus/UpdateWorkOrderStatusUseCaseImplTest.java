package com.fiap.challenge.workOrders.history.useCases.updateStatus;

import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.history.WorkOrderHistoryModel;
import com.fiap.challenge.workOrders.history.dto.UpdateWorkOrderStatusCommand;
import com.fiap.challenge.workOrders.history.repository.WorkOrderHistoryRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateWorkOrderStatusUseCaseImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderHistoryRepository workOrderHistoryRepository;

    @InjectMocks
    private UpdateWorkOrderStatusUseCaseImpl useCase;

    private UUID workOrderId;
    private WorkOrderModel workOrder;

    @BeforeEach
    void setUp() {
        workOrderId = UUID.randomUUID();

        workOrder = new WorkOrderModel();
        workOrder.setId(workOrderId);
        workOrder.setStatus(WorkOrderStatus.RECEIVED);
    }

    @Test
    void shouldThrowExceptionWhenWorkOrderNotFound() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            useCase.execute(new UpdateWorkOrderStatusCommand(workOrderId, WorkOrderStatus.COMPLETED));
        });

        assertTrue(exception.getMessage().contains(workOrderId.toString()));
        verify(workOrderRepository, times(1)).findById(workOrderId);
        verifyNoInteractions(workOrderHistoryRepository);
    }

    @Test
    void shouldReturnWithoutSavingWhenStatusIsSame() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));

        UpdateWorkOrderStatusCommand command = new UpdateWorkOrderStatusCommand(workOrderId, WorkOrderStatus.RECEIVED);

        WorkOrderModel result = useCase.execute(command);

        assertEquals(workOrder, result);
        verify(workOrderRepository, times(1)).findById(workOrderId);
        verifyNoInteractions(workOrderHistoryRepository);
    }

    @Test
    void shouldUpdateStatusAndSaveHistoryWhenStatusIsDifferent() {
        when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));
        when(workOrderHistoryRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UpdateWorkOrderStatusCommand command = new UpdateWorkOrderStatusCommand(workOrderId, WorkOrderStatus.COMPLETED, "Status changed");

        WorkOrderModel result = useCase.execute(command);

        assertEquals(WorkOrderStatus.COMPLETED, result.getStatus());

        verify(workOrderRepository, times(1)).findById(workOrderId);
        verify(workOrderHistoryRepository, times(1)).save(any(WorkOrderHistoryModel.class));
    }
}
