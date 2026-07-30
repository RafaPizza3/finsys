package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractLowRequestedAmountException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.InactiveCustomerException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractRequestedAmountValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerStatusValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertContractUseCaseImpl implements InsertContractUseCase {
    private final ContractGateway contractGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    private final CustomerStatusValidation customerStatusValidation;
    private final ContractRequestedAmountValidation contractRequestedAmountValidation;

    @Value("${finsys.interest-rate.monthly}")
    private Double monthlyInterestRate;
    private DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public ContractDomain execute(ContractDomain contract) throws CustomerNotFoundException, ContractLowRequestedAmountException, InactiveCustomerException {
        customerExistenceValidation.validate(contract.getCustomerId());
        customerStatusValidation.validate(contract.getCustomerId());

        String customerId = contract.getCustomerId();
        Double requestedAmount = contract.getRequestedAmount();
        Integer period = contract.getPeriod();

        Double installmentAmount = calcInstallmentAmount(requestedAmount, period, this.monthlyInterestRate);
        String formattedTotalAmount = this.df.format(installmentAmount * period).replace(',', '.');
        Double totalAmount = Double.parseDouble(formattedTotalAmount);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period);
        ContractStatus status = ContractStatus.ACTIVE;

        List<InstallmentDomain> installments = createInstallments(period, installmentAmount);

        contractRequestedAmountValidation.validate(requestedAmount);

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

        Double installmentAmount = (requestedAmount / period) + incValue;
        String formattedInstallmentAmount = this.df.format(installmentAmount).replace(',', '.');
        return Double.parseDouble(formattedInstallmentAmount);
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
}
