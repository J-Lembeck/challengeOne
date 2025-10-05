package com.fiap.mapper.customer;

import com.fiap.core.domain.customer.Customer;
import com.fiap.core.domain.customer.DocumentNumber;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.dto.customer.CreateCustomerRequest;
import com.fiap.dto.customer.CustomerResponse;
import com.fiap.dto.customer.UpdateCustomerRequest;
import com.fiap.persistence.entity.customer.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .documentNumber(customer.getDocumentNumber().getValue())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .vehicles(new ArrayList<>())
                .workOrders(new ArrayList<>())
                .build();
    }


    // --- CORREÇÃO AQUI ---
    public Customer toDomain(CustomerEntity entity) {
        try {
            return Customer.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .documentNumber(DocumentNumber.fromPersistence(entity.getDocumentNumber()))
                    .phone(entity.getPhone())
                    .email(entity.getEmail())
                    .createdAt(entity.getCreatedAt())
                    .updatedAt(entity.getUpdatedAt())
                    .build();
        } catch (DocumentNumberException e) {
            throw new IllegalStateException("Invalid document number found in the database for customer " + entity.getId(), e);
        }
    }

    public Customer toDomain(CreateCustomerRequest request) throws DocumentNumberException {
        return Customer.builder()
                .name(request.name())
                .documentNumber(DocumentNumber.of(request.documentNumber()))
                .phone(request.phone())
                .email(request.email())
                .build();
    }

    public Customer toDomain(UUID id, UpdateCustomerRequest request) throws DocumentNumberException {
        return Customer.builder()
                .id(id)
                .name(request.name())
                .documentNumber(DocumentNumber.of(request.documentNumber()))
                .phone(request.phone())
                .email(request.email())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getDocumentNumber()
        );
    }
}