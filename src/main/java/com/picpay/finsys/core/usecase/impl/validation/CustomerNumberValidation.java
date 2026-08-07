package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.InvalidNumberException;
import org.springframework.stereotype.Component;

@Component
public class CustomerNumberValidation {
    public void validate(String number) {
        try {
            int intNumber = Integer.parseInt(number);

            if (intNumber < 0) {
                throw new InvalidNumberException();
            }
        } catch (NumberFormatException | InvalidNumberException nfe) {
            throw new InvalidNumberException();
        }
    }
}