package com.fiap.challenge.customers.useCases.delete;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerDeletionException;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void execute(UUID id) {
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!customer.getVehicles().isEmpty() || !customer.getWorkOrders().isEmpty()) {
            throw new CustomerDeletionException(id);
        }

        customerRepository.delete(customer);
    }
}