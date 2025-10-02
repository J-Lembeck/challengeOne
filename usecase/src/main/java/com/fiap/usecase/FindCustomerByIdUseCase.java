package com.fiap.usecase;

import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;

import java.util.UUID;

public interface FindCustomerByIdUseCase {

    Customer execute(UUID customerId) throws NotFoundException, DocumentNumberException;
}
