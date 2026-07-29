package com.picpay.finsys.core.usecase.impl.validation;


import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.FinishedContractException;
import org.springframework.stereotype.Component;

@Component
public class ContractStatusValidation {
    public void validate(ContractDomain contract) throws CanceledContractException, FinishedContractException {
        if(contract.getStatus() == ContractStatus.CANCELED) {
            throw new CanceledContractException(contract.getId());
        }

        if(contract.getStatus() == ContractStatus.FINISHED) {
            throw new FinishedContractException(contract.getId());
        }
    }
}
