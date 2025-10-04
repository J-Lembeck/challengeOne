package com.fiap.dto.request;

import java.util.UUID;

public record UpdateUserRequest(UUID id, String name, String email, String password) {}
