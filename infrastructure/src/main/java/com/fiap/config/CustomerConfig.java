package com.fiap.config;

import com.fiap.application.gateway.CustomerGateway;
import com.fiap.application.gateway.DocumentNumberAvailableGateway;
import com.fiap.application.gateway.EmailAvailableGateway;
import com.fiap.application.usecaseimpl.CreateCustomerUseCaseImpl;
import com.fiap.application.usecaseimpl.DocumentNumberAvailableUseCaseImpl;
import com.fiap.application.usecaseimpl.EmailAvailableUseCaseImpl;
import com.fiap.usecase.CreateCustomerUseCase;
import com.fiap.usecase.DocumentNumberAvailableUseCase;
import com.fiap.usecase.EmailAvailableUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public DocumentNumberAvailableUseCase documentNumberAvailableUseCase(DocumentNumberAvailableGateway documentNumberAvailableGateway){
        return new DocumentNumberAvailableUseCaseImpl(documentNumberAvailableGateway) {
        };
    }

    @Bean
    public EmailAvailableUseCase emailAvailableUseCase(EmailAvailableGateway emailAvailableGateway){
        return new EmailAvailableUseCaseImpl(emailAvailableGateway) {
        };
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(DocumentNumberAvailableUseCase documentNumberAvailableUseCase, EmailAvailableUseCase emailAvailableUseCase, CustomerGateway customerGateway){
        return new CreateCustomerUseCaseImpl(documentNumberAvailableUseCase, emailAvailableUseCase, customerGateway) {
        };
    }
}
