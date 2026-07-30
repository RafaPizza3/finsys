package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.InvalidEmailException;
import org.springframework.stereotype.Component;

@Component
public class CustomerEmailValidation {
    public void validate(String email) throws InvalidEmailException {
        if(!email.matches("^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$")) {
            throw new InvalidEmailException();
        }
    }
}
