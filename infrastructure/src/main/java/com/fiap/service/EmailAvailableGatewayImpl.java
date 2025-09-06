package com.fiap.service;

import com.fiap.application.gateway.EmailAvailableGateway;
import com.fiap.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailAvailableGatewayImpl implements EmailAvailableGateway {

    final CustomerEntityRepository customerEntityRepository;

    public EmailAvailableGatewayImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Boolean emailAvailable(String email) {
        return !customerEntityRepository.existsByEmail(email);
    }
}
