package com.fiap.core.domain;

import com.fiap.core.exception.PasswordException;
import com.fiap.core.exception.enums.ErrorCodeEnum;

public class Password {
    private final String value;

    public Password(String value) throws PasswordException {
        this.isValidPassword(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void isValidPassword(String password) {
        if (password == null) {
            throw new PasswordException(ErrorCodeEnum.CAD0005.getMessage(), ErrorCodeEnum.CAD0005.getCode());
        }

        if (password.length() < 8) {
            throw new PasswordException(ErrorCodeEnum.CAD0006.getMessage(), ErrorCodeEnum.CAD0006.getCode());
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if ("!@#$%^&*()-+".indexOf(ch) >= 0) hasSpecial = true;
        }

        if (!hasUpper) {
      throw new PasswordException(
          ErrorCodeEnum.CAD0007.getMessage(), ErrorCodeEnum.CAD0007.getCode());
        }
        if (!hasLower) {
      throw new PasswordException(
          ErrorCodeEnum.CAD0008.getMessage(), ErrorCodeEnum.CAD0008.getCode());
        }
        if (!hasDigit) {
      throw new PasswordException(
          ErrorCodeEnum.CAD0009.getMessage(), ErrorCodeEnum.CAD0009.getCode());
        }
        if (!hasSpecial) {
            throw new PasswordException(ErrorCodeEnum.CAD0010.getMessage(), ErrorCodeEnum.CAD0010.getCode());
        }
    }

}
