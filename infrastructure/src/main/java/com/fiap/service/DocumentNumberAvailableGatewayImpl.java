package com.fiap.service;

import com.fiap.application.gateway.DocumentNumberAvailableGateway;
import com.fiap.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentNumberAvailableGatewayImpl implements DocumentNumberAvailableGateway {

    final CustomerEntityRepository customerEntityRepository;

    public DocumentNumberAvailableGatewayImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Boolean documentNumberAvailable(String documentNumber) {
        return !customerEntityRepository.existsByDocumentNumber(documentNumber);
    }
}
