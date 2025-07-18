package com.fiap.challenge.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.usecases.CreateUserUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserRequestDTO requestDTO) {
        UserModel createdUser = createUserUseCase.execute(requestDTO);
        UserResponseDTO responseBody = new UserResponseDTO(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}