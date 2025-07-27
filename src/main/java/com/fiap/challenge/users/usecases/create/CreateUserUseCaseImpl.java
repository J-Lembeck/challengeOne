package com.fiap.challenge.users.usecases.create;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.exception.users.UserAlreadyExistsException;
import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.dto.UserResponseDTO;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(CreateUserRequestDTO requestDTO) {
        userRepository.findByEmail(requestDTO.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException("O e-mail " + requestDTO.email() + " já está em uso.");
        });

        String hashedPassword = passwordEncoder.encode(requestDTO.password());

        UserModel newUser = UserModel.builder()
                .name(requestDTO.name())
                .email(requestDTO.email())
                .passwordHash(hashedPassword)
                .role(requestDTO.role())
                .build();

        UserModel savedUser = userRepository.save(newUser);

        return new UserResponseDTO(savedUser);
    }
}