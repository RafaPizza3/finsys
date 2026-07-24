package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.CustomerTooYoungException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerAgeValidation {
    Integer majorityAge = 18;

    @SneakyThrows
    public void validate(LocalDateTime birthDate) {
        if (birthDate.plusYears(this.majorityAge).isAfter(LocalDateTime.now())) {
            throw new CustomerTooYoungException();
        }
    }
}
