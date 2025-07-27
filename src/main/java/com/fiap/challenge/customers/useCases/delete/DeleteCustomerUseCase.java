package com.fiap.challenge.customers.useCases.delete;

import java.util.UUID;

public interface DeleteCustomerUseCase {
    public void execute(UUID id);
}