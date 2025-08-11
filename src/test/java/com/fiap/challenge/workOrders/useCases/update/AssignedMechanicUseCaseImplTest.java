package com.fiap.challenge.workOrders.useCases.update;

import java.util.UUID;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotAvailableException;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import com.fiap.challenge.users.usecases.find.FindUserByIdUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignedMechanicUseCaseImplTest {

    @Mock
    private FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private UpdateWorkOrderUseCase updateWorkOrderUseCase;

    @InjectMocks
    private AssignedMechanicUseCaseImpl assignedMechanicUseCase;

    private UUID workOrderId;
    private UUID mechanicId;
    private WorkOrderModel workOrderModel;
    private UserModel userModel;

    @BeforeEach
    void setup() {
        workOrderId = UUID.randomUUID();
        mechanicId = UUID.randomUUID();

        workOrderModel = new WorkOrderModel();
        workOrderModel.setStatus(WorkOrderStatus.RECEIVED);

        userModel = new UserModel();
    }

    @Test
    void shouldAssignMechanicAndUpdateStatusSuccessfully() {
        // Arrange
        when(findWorkOrderByIdUseCase.execute(workOrderId)).thenReturn(workOrderModel);
        when(findUserByIdUseCase.execute(mechanicId)).thenReturn(userModel);

        var input = new InputAssignMechanicDTO(mechanicId);

        // Act
        AssignedMechanicResponseDTO response = assignedMechanicUseCase.execute(workOrderId, input);

        // Assert
        assertTrue(response.success());
        assertEquals(WorkOrderStatus.IN_DIAGNOSIS, workOrderModel.getStatus());
        assertEquals(userModel, workOrderModel.getAssignedMechanic());

        verify(updateWorkOrderUseCase).execute(workOrderModel);
    }

    @Test
    void shouldThrowExceptionWhenStatusNotReceived() {
        // Arrange
        workOrderModel.setStatus(WorkOrderStatus.IN_PROGRESS);
        when(findWorkOrderByIdUseCase.execute(workOrderId)).thenReturn(workOrderModel);

        var input = new InputAssignMechanicDTO(mechanicId);

        // Act & Assert
        assertThrows(WorkOrderNotAvailableException.class, () -> {
            assignedMechanicUseCase.execute(workOrderId, input);
        });

        verify(findUserByIdUseCase, never()).execute(any());
        verify(updateWorkOrderUseCase, never()).execute(any());
    }
}
