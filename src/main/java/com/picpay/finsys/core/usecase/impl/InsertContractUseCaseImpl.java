package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertContractUseCaseImpl implements InsertContractUseCase {
    private final ContractGateway contractGateway;
    private final CustomerGateway customerGateway;

    @Override
    public ContractDomain execute(ContractDomain contract) throws CustomerNotFoundException {
        verifyCustomer(contract.getCustomerId());

        Double interestRate = 4.0;

        String customerId = contract.getCustomerId();
        Double value = contract.getValue();
        Integer period = contract.getPeriod();

        Double installmentAmount = calcInstallmentAmount(value, period, interestRate);
        Double totalAmount = installmentAmount * period;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period);
        ContractStatus status = ContractStatus.ACTIVE;

        List<InstallmentDomain> installments = createInstallments(period, installmentAmount);

        ContractDomain domain = createObject(
                customerId,
                value,
                totalAmount,
                interestRate,
                period,
                installmentAmount,
                startDate,
                endDate,
                status,
                installments
        );

        return contractGateway.insert(domain);
    }

    private Double calcInstallmentAmount(Double value, Integer period, Double interestRate) {
        Double incValue = value * interestRate / 100;
        Double installmentAmount = (value / period) + incValue;

        return installmentAmount;
    }

    private List<InstallmentDomain> createInstallments(
            Integer period,
            Double installmentAmount
    ) {
        List<InstallmentDomain> installments = new ArrayList<>();

        for (int i = 1; i <= period; i++) {
            InstallmentDomain installment = InstallmentDomain.builder()
                    .id(String.valueOf(i))
                    .amount(installmentAmount)
                    .status(InstallmentStatus.OPEN)
                    .dueDate(LocalDateTime.now().plusMonths(i))
                    .build();

            installments.add(installment);
        }

        return installments;
    }

    private ContractDomain createObject(
            String customerId,
            Double value,
            Double totalAmount,
            Double interestRate,
            Integer period,
            Double installmentAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ContractStatus status,
            List<InstallmentDomain> installments
    ) {
        return ContractDomain.builder()
                .customerId(customerId)
                .value(value)
                .totalAmount(totalAmount)
                .interestRate(interestRate)
                .period(period)
                .installmentAmount(installmentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .installments(installments)
                .build();
    }

    private void verifyCustomer(String customerId) throws CustomerNotFoundException {
        CustomerDomain customer = customerGateway.findById(customerId);

        if(customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
