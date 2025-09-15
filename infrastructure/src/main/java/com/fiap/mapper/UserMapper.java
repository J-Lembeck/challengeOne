package com.fiap.mapper;

import com.fiap.core.domain.User;
import com.fiap.core.exception.EmailException;
import com.fiap.dto.request.CreateUserRequest;
import com.fiap.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toDomain(CreateUserRequest request) throws EmailException {
        return new User(
                request.name(),
                request.email(),
                request.password()
        );
    }
}
