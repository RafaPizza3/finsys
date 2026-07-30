package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.NewLowerContractPeriodException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractPeriodUpdateValidation {
    public void validate(Integer requestPeriod, Integer originalPeriod) throws NewLowerContractPeriodException {
        if(requestPeriod < originalPeriod) {
            throw new NewLowerContractPeriodException();
        }
    }
}
