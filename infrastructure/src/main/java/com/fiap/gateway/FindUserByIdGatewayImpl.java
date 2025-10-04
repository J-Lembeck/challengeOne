package com.fiap.service;

import com.fiap.application.gateway.FindUserByIdGateway;
import com.fiap.application.gateway.UserGateway;
import com.fiap.core.domain.User;
import com.fiap.mapper.UserMapper;
import com.fiap.repository.UserEntityRepository;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class FindUserByIdGatewayImpl implements UserGateway {

    final UserEntityRepository userEntityRepository;
    final UserMapper userMapper;

    public FindUserByIdGatewayImpl(UserEntityRepository userEntityRepository, UserMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Boolean create(User user) {
        return null;
    }

    @Override
    public User findById(UUID userId) {
        return userEntityRepository.findById(userId).map(userMapper::toDomain).orElse(null);
    }
}
