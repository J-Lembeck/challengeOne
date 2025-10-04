package com.fiap.mapper;

import com.fiap.core.domain.Customer;
import com.fiap.core.domain.DocumentNumber;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.dto.request.CreateCustomerRequest;
import com.fiap.dto.request.UpdateCustomerRequest;
import com.fiap.dto.response.CustomerResponse;
import com.fiap.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                customer.getId(),
                customer.getName(),
                customer.getDocumentNumber().getValue(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public Customer toDomain(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                DocumentNumber.fromPersistence(customerEntity.getDocumentNumber()),
                customerEntity.getPhone(),
                customerEntity.getEmail(),
                customerEntity.getCreatedAt(),
                customerEntity.getUpdatedAt()
        );
    }

    public Customer toDomain(CreateCustomerRequest request) throws DocumentNumberException {
        return new Customer(
                request.name(),
                DocumentNumber.of(request.documentNumber()),
                request.phone(),
                request.email()
        );
    }

    public Customer toDomainUpdate(UpdateCustomerRequest request) throws DocumentNumberException {
        return new Customer(
                request.id(),
                request.name(),
                DocumentNumber.of(request.documentNumber()),
                request.phone(),
                request.email()
        );
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getDocumentNumber());
    }
}
