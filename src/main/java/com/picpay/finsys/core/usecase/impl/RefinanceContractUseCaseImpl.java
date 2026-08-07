package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.ContractType;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.CanceledContractException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.FinishedContractException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.impl.validation.ContractExistenceValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractStatusValidation;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.picpay.finsys.core.usecase.RefinanceContractUseCase;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefinanceContractUseCaseImpl implements RefinanceContractUseCase {
    private final ContractGateway contractGateway;

    private final ContractExistenceValidation contractExistenceValidation;
    private final ContractStatusValidation contractStatusValidation;

    @Value("${finsys.interest-rate.monthly}")
    private Double monthlyInterestRate;
    private final DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public ContractDomain execute(String contractId, Integer period, Integer monthsUntilCharge) throws ContractNotFoundException, CanceledContractException, FinishedContractException {
        System.out.println(contractId);
        contractExistenceValidation.validate(contractId);

        ContractType type = ContractType.REFINANCE;

        ContractDomain standardContract = contractGateway.findById(contractId);

        contractStatusValidation.validate(standardContract);

        Double dueAmount = calcDueAmount(standardContract.getInstallments(), standardContract.getTotalAmount());

        Double installmentAmount = calcInstallmentAmount(dueAmount, period, this.monthlyInterestRate);

        List<InstallmentDomain> installments = getInstallmentDomains(period, installmentAmount, monthsUntilCharge);

        Long daysOverdue = installments.getFirst().getDaysOverdue();

        Double totalAmount = installmentAmount * period;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period + monthsUntilCharge);
        ContractStatus status = ContractStatus.ACTIVE;

        ContractDomain canceledStandardContract = cancelStandardContractInstallments(standardContract);
        canceledStandardContract.setStatus(ContractStatus.CANCELED);

        contractGateway.update(canceledStandardContract);

        ContractDomain contract = createObject(
                standardContract.getCustomerId(),
                type,
                standardContract.getRequestedAmount(),
                dueAmount,
                totalAmount,
                this.monthlyInterestRate,
                period,
                installmentAmount,
                startDate,
                endDate,
                status,
                installments,
                daysOverdue,
                monthsUntilCharge
        );

        return contractGateway.insert(contract);
    }

    private Double calcDueAmount(List<InstallmentDomain> installments, Double totalAmount) {
        Double paidAmount = 0.0;
        for (InstallmentDomain installment : installments) {
            if(installment.getPaidAmount() == null) {
                continue;
            }

            paidAmount +=installment.getPaidAmount();
        }
        return totalAmount - paidAmount;
    }

    private Double calcInstallmentAmount(Double dueAmount, Integer period, Double interestRate) {
        double incValue = dueAmount * interestRate / 100;

        Double installmentAmount = (dueAmount / period) + incValue;
        String formattedInstallmentAmount = this.df.format(installmentAmount).replace(',', '.');
        return Double.parseDouble(formattedInstallmentAmount);
    }

    ContractDomain cancelStandardContractInstallments(ContractDomain contract) {
        for (int i = 0; i < contract.getInstallments().size(); i++) {
            InstallmentDomain installment = contract.getInstallments().get(i);
            if (installment.getStatus() == InstallmentStatus.PAID) {
                continue;
            }

            installment.setStatus(InstallmentStatus.CANCELED);

            contract.getInstallments().remove(i);
            contract.getInstallments().add(i, installment);
        }

        return contract;
    }

    private List<InstallmentDomain> getInstallmentDomains(Integer period, Double installmentAmount, Integer monthsUntilCharge) {
        List<InstallmentDomain> installments = new ArrayList<>();

        for (int i = 0; i < period; i++) {
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
            Double originalDueAmount,
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
                .originalDueAmount(originalDueAmount)
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

