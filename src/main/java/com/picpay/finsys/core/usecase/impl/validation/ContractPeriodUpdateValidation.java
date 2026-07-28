package com.picpay.finsys.core.usecase.impl.validation;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractPeriodUpdateValidation {
    public void validate(Integer requestPeriod, Integer originalPeriod) throws BadRequestException {
        if(requestPeriod < originalPeriod) {
            throw new BadRequestException("new contract value must be bigger than or equals original value");
        }
    }
}
