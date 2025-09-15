package com.fiap.service;

import com.fiap.application.gateway.CreateUserGateway;
import com.fiap.core.domain.User;
import com.fiap.mapper.UserMapper;
import com.fiap.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserGatewayImpl implements CreateUserGateway {

    final UserEntityRepository userEntityRepository;
    final UserMapper userMapper;

    public CreateUserGatewayImpl(UserEntityRepository userEntityRepository, UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Boolean create(User user) {
        try {
            System.out.println("vai salvar");
            System.out.println("uuid valor:" +  user.getId());
            userEntityRepository.save(userMapper.toEntity(user));
            return true;
        } catch (Exception e) {
            System.out.println("caiu no erro");
            return false;
        }
    }
}
