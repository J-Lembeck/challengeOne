package com.fiap.config;

import com.fiap.application.gateway.UserGateway;
import com.fiap.application.usecaseimpl.CreateUserUseCaseImpl;
import com.fiap.application.usecaseimpl.DeleteUserUseCaseImpl;
import com.fiap.application.usecaseimpl.FindUserByIdUseCaseImpl;
import com.fiap.application.usecaseimpl.UpdateUserUseCaseImpl;
import com.fiap.usecase.CreateUserUseCase;
import com.fiap.usecase.DeleteUserUseCase;
import com.fiap.usecase.FindUserByIdUseCase;
import com.fiap.usecase.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway){
        return new CreateUserUseCaseImpl(userGateway) {
        };
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway userGateway){
        return new UpdateUserUseCaseImpl(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway userGateway){
        return new DeleteUserUseCaseImpl(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway){
        return new FindUserByIdUseCaseImpl(userGateway);
    }

}
