package com.fiap.core.exception.enums;

public enum ErrorCodeEnum {

    CAD0001("Documento Inválido", "CAD-0001"),
    CAD0002("Documento já cadastrado!", "CAD-0002"),
    CAD0003("Email já cadastrado!", "CAD-0003"),
    CAD0004("Erro na criação do cliente", "CAD-0004")
    ;

    private String message;
    private String code;

    ErrorCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
