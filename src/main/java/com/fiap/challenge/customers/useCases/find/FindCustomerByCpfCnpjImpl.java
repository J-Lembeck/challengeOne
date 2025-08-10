package com.fiap.challenge.customers.useCases.find;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCustomerByCpfCnpjImpl implements FindCustomerByCpfCnpj {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO execute(String cpfCnpj) {
        return customerRepository.findByCpfCnpj(cpfCnpj)
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getCpfCnpj(),
                        customer.getPhone(),
                        customer.getEmail(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()
                ))
                .orElseThrow(() -> new CustomerNotFoundException(cpfCnpj));
    }
}