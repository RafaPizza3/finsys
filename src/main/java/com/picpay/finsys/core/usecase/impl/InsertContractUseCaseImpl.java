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
import com.picpay.finsys.core.usecase.impl.common.ChangeContract;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertContractUseCaseImpl extends ChangeContract implements InsertContractUseCase {
    private final ContractGateway contractGateway;
    private final CustomerGateway customerGateway;

    Double monthlyInterestRate = 4.0;
    Integer minimumRequestedAmount = 1000;

    @Override
    public ContractDomain execute(ContractDomain contract) throws BadRequestException {
        super.verifyCustomer(contract.getCustomerId(), customerGateway);

        String customerId = contract.getCustomerId();
        Double requestedAmount = contract.getRequestedAmount();
        Integer period = contract.getPeriod();

        Double installmentAmount = calcInstallmentAmount(requestedAmount, period, this.monthlyInterestRate);
        Double totalAmount = installmentAmount * period;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period);
        ContractStatus status = ContractStatus.ACTIVE;

        List<InstallmentDomain> installments = createInstallments(period, installmentAmount);

        verifyContractRequestedAmount(requestedAmount);

        ContractDomain domain = createObject(
                customerId,
                requestedAmount,
                totalAmount,
                this.monthlyInterestRate,
                period,
                installmentAmount,
                startDate,
                endDate,
                status,
                installments
        );

        return contractGateway.insert(domain);
    }

    private Double calcInstallmentAmount(Double requestedAmount, Integer period, Double interestRate) {
        double incValue = requestedAmount * interestRate / 100;

        return (requestedAmount / period) + incValue;
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
            Double requestedAmount,
            Double totalAmount,
            Double monthlyInterestRate,
            Integer period,
            Double installmentAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ContractStatus status,
            List<InstallmentDomain> installments
    ) {
        return ContractDomain.builder()
                .customerId(customerId)
                .requestedAmount(requestedAmount)
                .totalAmount(totalAmount)
                .monthlyInterestRate(monthlyInterestRate)
                .period(period)
                .installmentAmount(installmentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .installments(installments)
                .build();
    }

    private void verifyContractRequestedAmount(Double requestedAmount) throws BadRequestException {
        if (requestedAmount < this.minimumRequestedAmount) {
            throw new BadRequestException("contract requested amount must be at least 1000");
        }
    }
}
