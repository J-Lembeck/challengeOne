package com.fiap.dto.commom;

import java.util.List;

public record ErrorResponse(String code, String message, List<ValidationError> validations){}
