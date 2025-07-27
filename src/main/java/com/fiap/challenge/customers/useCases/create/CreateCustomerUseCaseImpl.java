package com.fiap.challenge.customers.useCases.create;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fiap.challenge.customers.dto.CreateCustomerRequestDTO;
import com.fiap.challenge.customers.dto.CustomerResponseDTO;
import com.fiap.challenge.customers.entity.CustomerModel;
import com.fiap.challenge.customers.repository.CustomerRepository;
import com.fiap.challenge.shared.exception.customer.CustomerAlreadyExistsException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponseDTO execute(CreateCustomerRequestDTO request) {
        if (customerRepository.existsByCpfCnpj(request.cpfCnpj())) {
            throw new CustomerAlreadyExistsException("Customer with this CPF/CNPJ already exists.");
        }

        CustomerModel newCustomer = CustomerModel.builder()
                .name(request.name())
                .cpfCnpj(request.cpfCnpj())
                .phone(request.phone())
                .email(request.email())
                .vehicles(new ArrayList<>())
                .workOrders(new ArrayList<>())
                .build();

        CustomerModel savedCustomer = customerRepository.save(newCustomer);

        return new CustomerResponseDTO(
            savedCustomer.getId(),
            savedCustomer.getName(),
            savedCustomer.getCpfCnpj(),
            savedCustomer.getPhone(),
            savedCustomer.getEmail(),
            savedCustomer.getCreatedAt(),
            savedCustomer.getUpdatedAt()
        );
    }
}