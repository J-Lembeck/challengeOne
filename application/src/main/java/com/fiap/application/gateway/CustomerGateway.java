package com.fiap.application.gateway;

import com.fiap.core.domain.Customer;
import com.fiap.core.exception.DocumentNumberException;

import java.util.Optional;
import java.util.UUID;

public interface CustomerGateway {
    Boolean create(Customer customer);
    Optional<Customer> findById(UUID customerId) throws DocumentNumberException;
    void delete(Customer customer);
}
