package com.fiap.config;

import com.fiap.application.gateway.CreateUserGateway;
import com.fiap.application.usecaseimpl.CreateUserUseCaseImpl;
import com.fiap.usecase.CreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(CreateUserGateway createUserGateway){
        return new CreateUserUseCaseImpl(createUserGateway) {
        };
    }

}
