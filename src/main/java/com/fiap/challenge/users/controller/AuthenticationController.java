package com.fiap.challenge.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.shared.security.UserPrincipal;
import com.fiap.challenge.shared.security.jwt.TokenService;
import com.fiap.challenge.users.dto.LoginRequestDTO;
import com.fiap.challenge.users.dto.LoginResponseDTO;
import com.fiap.challenge.users.entity.UserModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        UserModel userModel = ((UserPrincipal) auth.getPrincipal()).getUserModel();
        String token = tokenService.generateToken(userModel);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
