package com.fiap.gateway;

import com.fiap.application.gateway.EmailAvailableGateway;
import com.fiap.persistence.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailAvailableGatewayImpl implements EmailAvailableGateway {

    private final CustomerEntityRepository customerEntityRepository;

    public EmailAvailableGatewayImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Boolean emailAvailable(String email) {
        return !customerEntityRepository.existsByEmail(email);
    }
}
