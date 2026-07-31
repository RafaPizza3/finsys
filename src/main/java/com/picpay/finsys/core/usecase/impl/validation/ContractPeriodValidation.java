package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.ContractLowPeriodException;
import org.springframework.stereotype.Component;

@Component
public class ContractPeriodValidation {
    public void validate(Integer period) throws ContractLowPeriodException {
        if(period < 6) {
            throw new ContractLowPeriodException();
        }
    }
}
