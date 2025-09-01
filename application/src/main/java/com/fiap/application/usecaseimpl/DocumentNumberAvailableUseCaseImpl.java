package com.fiap.application.usecaseimpl;

import com.fiap.application.gateway.DocumentNumberAvailableGateway;
import com.fiap.usecase.DocumentNumberAvailableUseCase;

public class DocumentNumberAvailableUseCaseImpl implements DocumentNumberAvailableUseCase {

    public DocumentNumberAvailableGateway documentNumberAvailableGateway;

    public DocumentNumberAvailableUseCaseImpl(DocumentNumberAvailableGateway documentNumberAvailableGateway) {
        this.documentNumberAvailableGateway = documentNumberAvailableGateway;
    }

    @Override
    public Boolean documentNumberAvailable(String documentNumber) {
        return documentNumberAvailableGateway.documentNumberAvailable(documentNumber);
    }
}
