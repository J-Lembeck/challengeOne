package com.fiap.usecase;

import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;

public interface CreateCustomerUseCase {

    void execute(Customer customer) throws DocumentNumberException, EmailException, InternalServerErrorException;
}
