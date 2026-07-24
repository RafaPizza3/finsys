package com.picpay.finsys.core.usecase.impl.validation;

import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractRequestedAmountValidation {
    Integer minimumRequestedAmount = 1000;

    @SneakyThrows
    public void validate(Double requestedAmount) {
        if (requestedAmount < this.minimumRequestedAmount) {
            throw new BadRequestException("contract requested amount must be at least 1000");
        }
    }
}
