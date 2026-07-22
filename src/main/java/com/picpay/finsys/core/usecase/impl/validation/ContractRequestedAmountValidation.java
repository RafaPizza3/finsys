package com.picpay.finsys.core.usecase.impl.validation;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractRequestedAmountValidation {
    Integer minimumRequestedAmount = 1000;

    public void validate(Double requestedAmount) throws BadRequestException {
        if (requestedAmount < this.minimumRequestedAmount) {
            throw new BadRequestException("contract requested amount must be at least 1000");
        }
    }
}
