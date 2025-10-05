package com.fiap.gateway.customer;

import com.fiap.application.gateway.customer.CustomerGateway;
import com.fiap.core.domain.customer.Customer;
import com.fiap.core.exception.DocumentNumberException;
import com.fiap.mapper.customer.CustomerMapper;
import com.fiap.persistence.entity.customer.CustomerEntity;
import com.fiap.persistence.repository.customer.CustomerEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRepositoryGateway implements CustomerGateway {
    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryGateway(CustomerEntityRepository customerEntityRepository, CustomerMapper customerMapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer create(Customer customer) throws DocumentNumberException {
        CustomerEntity customerEntity = customerEntityRepository.save(customerMapper.toEntity(customer));
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Customer update(Customer customer) throws DocumentNumberException {
        CustomerEntity customerEntity = customerEntityRepository.save(customerMapper.toEntity(customer));
        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) throws DocumentNumberException {
        Optional<CustomerEntity> customerEntity = customerEntityRepository.findById(customerId);

        return customerEntity.isPresent() ? Optional.of(customerMapper.toDomain(customerEntity.get())) : Optional.empty();
    }

    @Override
    public Optional<Customer> findByDocumentNumber(String documentNumber) throws DocumentNumberException {
        Optional<CustomerEntity> customerEntity = customerEntityRepository.findByDocumentNumber(documentNumber);

        return customerEntity.isPresent() ? Optional.of(customerMapper.toDomain(customerEntity.get())) : Optional.empty();
    }

    @Override
    public void delete(Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerEntityRepository.delete(customerEntity);
    }
}
