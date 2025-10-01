package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.CustomerGateway;
import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;
import com.fiap.core.exception.enums.ErrorCodeEnum;
import com.fiap.usecase.DeleteCustomerUseCase;

import java.util.UUID;

public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {

    private final CustomerGateway customerGateway;

    public DeleteCustomerUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public void execute(UUID id) throws DocumentNumberException, NotFoundException {
        Customer customer = customerGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CUST0001.getMessage(), ErrorCodeEnum.CUST0001.getCode()));

        customerGateway.delete(customer);
    }
}