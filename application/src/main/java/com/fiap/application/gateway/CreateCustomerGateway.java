package com.fiap.application.gateway;

import com.fiap.core.domain.Customer;

public interface CreateCustomerGateway {
    Boolean create(Customer customer);
}
