package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.ContractLowRequestedAmountException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractRequestedAmountValidation {
    Integer minimumRequestedAmount = 1000;

    public void validate(Double requestedAmount) throws ContractLowRequestedAmountException {
        if (requestedAmount < this.minimumRequestedAmount) {
            throw new ContractLowRequestedAmountException();
        }
    }
}
