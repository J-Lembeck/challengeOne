package com.fiap.controller;

import com.fiap.core.exception.EmailException;
import com.fiap.dto.request.CreateUserRequest;
import com.fiap.dto.response.BaseResponse;
import com.fiap.mapper.UserMapper;
import com.fiap.usecase.CreateUserUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    final CreateUserUseCase createUserUseCase;
    final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase, UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping("/createUser")
    public BaseResponse<String> createUser(@RequestBody CreateUserRequest request) throws EmailException {
        createUserUseCase.execute(userMapper.toDomain(request));
        return BaseResponse.<String>builder().success(true).message("Usuário criado com sucesso").build();
    }
}
