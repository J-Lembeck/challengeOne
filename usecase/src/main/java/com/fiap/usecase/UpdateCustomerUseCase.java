package com.fiap.usecase;

import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.core.exception.EmailException;
import com.fiap.core.exception.InternalServerErrorException;
import com.fiap.core.exception.NotFoundException;

public interface UpdateCustomerUseCase {

    Customer execute(Customer customer) throws DocumentNumberException, NotFoundException, EmailException, InternalServerErrorException;
}
