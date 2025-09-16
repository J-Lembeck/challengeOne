package com.fiap.controller;

import com.fiap.core.exception.EmailException;
import com.fiap.dto.request.CreateUserRequest;
import com.fiap.dto.response.BaseResponse;
import com.fiap.dto.response.UserResponse;
import com.fiap.mapper.UserMapper;
import com.fiap.usecase.CreateUserUseCase;
import com.fiap.usecase.FindUserByIdUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    final CreateUserUseCase createUserUseCase;
    final FindUserByIdUseCase findUserByIdUseCase;
    final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase, FindUserByIdUseCase findUserByIdUseCase, UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.userMapper = userMapper;
        this.findUserByIdUseCase = findUserByIdUseCase;
    }

    @PostMapping("/createUser")
    public BaseResponse<String> createUser(@RequestBody CreateUserRequest request) throws EmailException {
        createUserUseCase.execute(userMapper.toDomain(request));
        return BaseResponse.<String>builder().success(true).message("Usuário criado com sucesso").build();
    }

  @GetMapping("/{id}")
  public BaseResponse<UserResponse> getById(@PathVariable UUID id) {
    var user = findUserByIdUseCase.execute(id);
    return BaseResponse.<UserResponse>builder()
        .success(true)
        .result(userMapper.toResponse(user))
        .build();
  }
}
