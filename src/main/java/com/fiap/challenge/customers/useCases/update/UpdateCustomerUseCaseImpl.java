package com.fiap.challenge.customers.useCases.update;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerAlreadyExistsException;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;
import com.fiap.challenge.users.dto.UpdateCustomerRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponseDTO execute(UUID id, UpdateCustomerRequestDTO request) {
        CustomerModel customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!customerToUpdate.getCpfCnpj().equals(request.cpfCnpj())) {
            customerRepository.findByCpfCnpjAndIdNot(request.cpfCnpj(), id)
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistsException("The new CPF/CNPJ is already in use by another customer.");
                });
        }

        customerToUpdate.setName(request.name());
        customerToUpdate.setCpfCnpj(request.cpfCnpj());
        customerToUpdate.setPhone(request.phone());
        customerToUpdate.setEmail(request.email());

        CustomerModel updatedCustomer = customerRepository.save(customerToUpdate);

        return new CustomerResponseDTO(
            updatedCustomer.getId(),
            updatedCustomer.getName(),
            updatedCustomer.getCpfCnpj(),
            updatedCustomer.getPhone(),
            updatedCustomer.getEmail(),
            updatedCustomer.getCreatedAt(),
            updatedCustomer.getUpdatedAt()
        );
    }
}