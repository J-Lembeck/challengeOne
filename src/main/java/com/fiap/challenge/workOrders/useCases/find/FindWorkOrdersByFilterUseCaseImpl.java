package com.fiap.challenge.workOrders.useCases.find;

import com.fiap.challenge.workOrders.dto.WorkOrderFilterDTO;
import com.fiap.challenge.workOrders.dto.WorkOrderResumeDTO;
import com.fiap.challenge.workOrders.mapper.WorkOrderMapper;
import com.fiap.challenge.workOrders.repository.WorkOrderRepository;
import com.fiap.challenge.workOrders.repository.WorkOrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindWorkOrdersByFilterUseCaseImpl implements FindWorkOrdersByFilterUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    @Override
    public List<WorkOrderResumeDTO> execute(WorkOrderFilterDTO filterDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(filterDTO.getSortDirection()), filterDTO.getSortBy());

        var workOrders = workOrderRepository.findAll(WorkOrderSpecification.list(filterDTO), sort);

        List<WorkOrderResumeDTO> listWorkOrders = new ArrayList<>();

        for (var workOrder : workOrders) {
            var worOrderResume = workOrderMapper.toWorkOrderResumeDTO(workOrder);
            listWorkOrders.add(worOrderResume);
        }

        return listWorkOrders;
    }
}
