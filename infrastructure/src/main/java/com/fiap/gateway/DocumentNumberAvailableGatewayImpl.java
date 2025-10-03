package com.fiap.gateway;

import com.fiap.application.gateway.DocumentNumberAvailableGateway;
import com.fiap.persistence.repository.CustomerEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentNumberAvailableGatewayImpl implements DocumentNumberAvailableGateway {

    private final CustomerEntityRepository customerEntityRepository;

    public DocumentNumberAvailableGatewayImpl(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    @Override
    public Boolean documentNumberAvailable(String documentNumber) {
        return !customerEntityRepository.existsByDocumentNumber(documentNumber);
    }
}
