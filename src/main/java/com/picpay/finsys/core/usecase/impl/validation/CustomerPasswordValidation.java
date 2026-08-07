package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class CustomerPasswordValidation {
    public void validate(String password) throws InvalidPasswordException {
        if (!password.matches("^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8}$")) {
            throw new InvalidPasswordException();
        }
    }
}
