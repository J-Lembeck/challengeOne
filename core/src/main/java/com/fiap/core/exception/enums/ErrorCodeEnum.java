package com.fiap.core.exception.enums;

public enum ErrorCodeEnum {

    CAD0001("Documento Inválido", "CAD-0001"),
    CAD0002("Documento já cadastrado!", "CAD-0002"),
    CAD0003("Email já cadastrado!", "CAD-0003"),
    CAD0004("Erro na criação do cliente", "CAD-0004"),
    CAD0005("A senha não pode ser nula", "CAD-0005"),
    CAD0006("A senha deve ter pelo menos 8 caracteres", "CAD-0006"),
    CAD0007("A senha deve conter pelo menos uma letra maiúscula", "CAD-0007"),
    CAD0008("A senha deve conter pelo menos uma letra minúscula", "CAD-0008"),
    CAD0009("A senha deve conter pelo menos um número", "CAD-0009"),
    CAD0010("A senha deve conter pelo menos um caractere especial (!@#$%^&*()-+)", "CAD-0010"),
    CAD0011("Erro ao validar senha", "CAD-0011")
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
