package com.fiap.challenge.workOrders.useCases.update;

import com.fiap.challenge.shared.exception.workOrder.WorkOrderNotAvailableException;
import com.fiap.challenge.shared.model.ResponseApi;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.usecases.find.FindUserByIdUseCase;
import com.fiap.challenge.workOrders.dto.AssignedMechanicResponseDTO;
import com.fiap.challenge.workOrders.dto.InputAssignMechanicDTO;
import com.fiap.challenge.workOrders.entity.WorkOrderModel;
import com.fiap.challenge.workOrders.entity.enums.WorkOrderStatus;
import com.fiap.challenge.workOrders.useCases.find.FindWorkOrderByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssignedMechanicUseCaseImpl implements AssignedMechanicUseCase{

    private final FindWorkOrderByIdUseCase findWorkOrderByIdUseCase;
    private final UpdateWorkOrderUseCase updateWorkOrderUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public AssignedMechanicUseCaseImpl(FindWorkOrderByIdUseCase findWorkOrderByIdUseCase, UpdateWorkOrderUseCase updateWorkOrderUseCase, FindUserByIdUseCase findUserByIdUseCase) {
        this.findWorkOrderByIdUseCase = findWorkOrderByIdUseCase;
        this.updateWorkOrderUseCase = updateWorkOrderUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @Override
    public ResponseApi<AssignedMechanicResponseDTO> execute(UUID workOrderId, InputAssignMechanicDTO inputAssignMechanicDTO) {
        ResponseApi<AssignedMechanicResponseDTO> responseApi = new ResponseApi<>();
        WorkOrderModel workOrderModel = findWorkOrderByIdUseCase.execute(workOrderId);

        if (workOrderModel.getStatus() != WorkOrderStatus.RECEIVED) {
            throw new WorkOrderNotAvailableException((workOrderId));
        }

        UserModel userModel = findUserByIdUseCase.execute(inputAssignMechanicDTO.mechanicId());

        workOrderModel.setAssignedMechanic(userModel);
        workOrderModel.setStatus(WorkOrderStatus.IN_DIAGNOSIS);
        updateWorkOrderUseCase.execute(workOrderModel);

        return responseApi.of(HttpStatus.OK, "Mecânico atribuído com sucesso!",
                new AssignedMechanicResponseDTO(true));
    }
}
