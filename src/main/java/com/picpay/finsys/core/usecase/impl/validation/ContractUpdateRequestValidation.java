package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.exception.NullUpdateRequestException;
import org.springframework.stereotype.Component;

@Component
public class ContractUpdateRequestValidation {
    public void validate(ContractDomain request) throws NullUpdateRequestException {
        if (request.getCustomerId() == null && request.getRequestedAmount() == null && request.getPeriod() == null) {
            throw new NullUpdateRequestException();
        }
    }
}
