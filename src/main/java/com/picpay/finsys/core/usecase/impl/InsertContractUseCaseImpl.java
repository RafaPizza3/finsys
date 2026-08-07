package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.ContractType;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractLowPeriodException;
import com.picpay.finsys.core.exception.ContractLowRequestedAmountException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.exception.InactiveCustomerException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractPeriodValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractRequestedAmountValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerStatusValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertContractUseCaseImpl implements InsertContractUseCase {
    private final ContractGateway contractGateway;
    private final CustomerGateway customerGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    private final CustomerStatusValidation customerStatusValidation;
    private final ContractRequestedAmountValidation contractRequestedAmountValidation;
    private final ContractPeriodValidation contractPeriodValidation;

    @Value("${finsys.interest-rate.monthly}")
    private Double monthlyInterestRate;
    private final DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public ContractDomain execute(ContractDomain contract, String username) throws CustomerNotFoundException, ContractLowRequestedAmountException, InactiveCustomerException, ContractLowPeriodException {
        String customerId = customerGateway.findByEmail(username).getId();

        customerExistenceValidation.validate(customerId);
        customerStatusValidation.validate(customerId);

        Double requestedAmount = contract.getRequestedAmount();
        Integer period = contract.getPeriod();
        Integer monthsUntilCharge = contract.getMonthsUntilCharge();

        contractRequestedAmountValidation.validate(requestedAmount);
        contractPeriodValidation.validate(period);

        Double installmentAmount = calcInstallmentAmount(requestedAmount, period, this.monthlyInterestRate);
        String formattedTotalAmount = this.df.format(installmentAmount * period).replace(',', '.');
        Double totalAmount = Double.parseDouble(formattedTotalAmount);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period + monthsUntilCharge);
        ContractStatus status = ContractStatus.ACTIVE;
        ContractType type = ContractType.STANDARD;

        List<InstallmentDomain> installments = getInstallmentDomains(period, installmentAmount, monthsUntilCharge);

        ContractDomain domain = createObject(
                customerId,
                type,
                requestedAmount,
                totalAmount,
                this.monthlyInterestRate,
                period,
                installmentAmount,
                startDate,
                endDate,
                status,
                installments,
                installments.get(0).getDaysOverdue(),
                monthsUntilCharge
        );

        return contractGateway.insert(domain);
    }

    private Double calcInstallmentAmount(Double requestedAmount, Integer period, Double interestRate) {
        double incValue = requestedAmount * interestRate / 100;

        Double installmentAmount = (requestedAmount / period) + incValue;
        String formattedInstallmentAmount = this.df.format(installmentAmount).replace(',', '.');
        return Double.parseDouble(formattedInstallmentAmount);
    }

    private List<InstallmentDomain> getInstallmentDomains(Integer period, Double installmentAmount, Integer monthsUntilCharge) {
        List<InstallmentDomain> installments = new ArrayList<>();

        for (int i = 0; i <= period; i++) {
            LocalDateTime chargeDate = LocalDateTime.now().plusMonths(i + monthsUntilCharge);
            InstallmentDomain installment = InstallmentDomain.builder()
                    .id(String.valueOf(i + 1))
                    .amount(installmentAmount)
                    .status(InstallmentStatus.OPEN)
                    .dueDate(LocalDateTime.now().plusMonths(i + 1 + monthsUntilCharge))
                    .chargeDate(chargeDate)
                    .daysOverdue((ChronoUnit.DAYS.between(chargeDate.plusMonths(1), LocalDateTime.now())))
                    .build();

            installments.add(installment);
        }

        return installments;
    }

    private ContractDomain createObject(
            String customerId,
            ContractType type,
            Double requestedAmount,
            Double totalAmount,
            Double monthlyInterestRate,
            Integer period,
            Double installmentAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ContractStatus status,
            List<InstallmentDomain> installments,
            Long daysOverdue,
            Integer monthsUntilCharge
    ) {
        return ContractDomain.builder()
                .customerId(customerId)
                .type(type)
                .requestedAmount(requestedAmount)
                .totalAmount(totalAmount)
                .monthlyInterestRate(monthlyInterestRate)
                .period(period)
                .installmentAmount(installmentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .installments(installments)
                .daysOverdue(daysOverdue)
                .monthsUntilCharge(monthsUntilCharge)
                .build();
    }
}
