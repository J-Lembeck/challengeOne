package com.fiap.challenge.users.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fiap.challenge.shared.exception.users.UserAlreadyExistsException;
import com.fiap.challenge.users.dto.CreateUserRequestDTO;
import com.fiap.challenge.users.entity.UserModel;
import com.fiap.challenge.users.repository.UserRepository;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel execute(CreateUserRequestDTO requestDTO) {
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

        return userRepository.save(newUser);
    }
}