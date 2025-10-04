package com.fiap.usecase;

import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.NotFoundException;

public interface FindCustomerByDocumentUseCase {

    Customer execute(String documentNumber) throws DocumentNumberException, NotFoundException;
}