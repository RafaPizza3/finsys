package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.exception.InactiveCustomerException;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.DeleteCustomerUseCase;
import com.picpay.finsys.core.usecase.impl.validation.CustomerDeleteValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {
    private final CustomerGateway customerGateway;

    private final CustomerDeleteValidation customerDeleteValidation;

    @Override
    public void execute(String id) throws CustomerNotFoundException, CustomerHasContractException, InactiveCustomerException {
        CustomerDomain customer = customerGateway.findById(id);
        customerDeleteValidation.validate(customer);
        customerGateway.delete(id);
    }
}
