package com.fiap.service;

import com.fiap.application.gateway.CreateCustomerGateway;
import com.fiap.core.domain.Customer;
import com.fiap.mapper.CustomerMapper;
import com.fiap.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerGatewayImpl implements CreateCustomerGateway {
    final CustomerEntityRepository customerEntityRepository;
    final CustomerMapper customerMapper;

    public CreateCustomerGatewayImpl(CustomerEntityRepository customerEntityRepository, CustomerMapper customerMapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Boolean create(Customer customer) {
        try {
            System.out.println("vai salvar");
            System.out.println("uuid valor:" +  customer.getId());
            customerEntityRepository.save(customerMapper.toEntity(customer));
            return true;
        } catch (Exception e) {
            System.out.println("caiu no erro");
            return false;
        }
    }
}
