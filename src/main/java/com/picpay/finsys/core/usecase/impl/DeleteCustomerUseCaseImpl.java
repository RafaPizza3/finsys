package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.CustomerHasContractException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.DeleteCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final ContractGateway contractGateway;

    @Override
    public void execute(String id) throws CustomerNotFoundException, CustomerHasContractException {
        verifyCustomerExistence(id);
        verifyContractsStatus(id);
        customerGateway.delete(id);
    }

    private void verifyCustomerExistence(String id) throws CustomerNotFoundException {
        CustomerDomain domain = customerGateway.findById(id);
        if(domain == null) {
            throw new CustomerNotFoundException(id);
        }
    }

    private void verifyContractsStatus(String id) throws CustomerHasContractException {
        Integer activeContractsAmount = contractGateway.countActiveContractsByCustomerId(id, ContractStatus.ACTIVE);

        if (activeContractsAmount > 0) {
            throw new CustomerHasContractException();
        }
    }
}
