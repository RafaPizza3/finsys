package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.CustomerTooYoungException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerAgeValidation {
    Integer majorityAge = 18;

    public void validate(LocalDateTime birthDate) throws CustomerTooYoungException {
        if (birthDate.plusYears(this.majorityAge).isAfter(LocalDateTime.now())) {
            throw new CustomerTooYoungException();
        }
    }
}
