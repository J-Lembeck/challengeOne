package com.fiap.mapper;

import com.fiap.core.domain.user.User;
import com.fiap.core.exception.EmailException;
import com.fiap.dto.request.CreateUserRequest;
import com.fiap.dto.request.UpdateUserRequest;
import com.fiap.dto.response.UserResponse;
import com.fiap.persistence.entity.user.UserEntity;
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

    public User toDomainUpdate(UpdateUserRequest request) throws EmailException {
        return new User(
                request.id(),
                request.name(),
                request.email(),
                request.password()
        );
    }

    public User toDomain(UserEntity entity) {
        User user = null;
        try{
            user  = new User(
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPasswordHash(),
                    entity.getCreatedAt(),
                    entity.getUpdatedAt()
            );
        } catch (RuntimeException | EmailException e) {
            new EmailException("Error mapping UserEntity to User", "COD21");
        }
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
