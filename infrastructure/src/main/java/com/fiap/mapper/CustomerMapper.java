package com.fiap.mapper;

import com.fiap.core.domain.Customer;
import com.fiap.core.domain.DocumentNumber;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.dto.request.CreateCustomerRequest;
import com.fiap.entity.CustomerEntity;
import org.springframework.stereotype.Component;

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

    public Customer toCustomer(CreateCustomerRequest request) throws DocumentNumberException {
        return new Customer(
                request.name(),
                new DocumentNumber(request.documentNumber()),
                request.phone(),
                request.email()
        );
    }
}
