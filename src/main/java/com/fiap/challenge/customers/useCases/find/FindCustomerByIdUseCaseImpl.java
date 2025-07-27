package com.fiap.challenge.customers.useCases.find;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByIdUseCaseImpl implements FindCustomerByIdUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO execute(UUID id) {
        return customerRepository.findById(id)
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getCpfCnpj(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()
                ))	
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}