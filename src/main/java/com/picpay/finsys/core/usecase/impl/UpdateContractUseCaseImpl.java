package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateContractUseCaseImpl implements UpdateContractUseCase {
    private final ContractGateway contractGateway;

    @Override
    public ContractDomain execute(String id, ContractDomain contract) throws ContractNotFoundException {
        ContractDomain bdContract = contractGateway.findById(id);
        if (bdContract == null) {
            throw new ContractNotFoundException(id);
        }

        String customerId = contract.getCustomerId();
        Double totalAmount = contract.getTotalAmount();
        Integer period = contract.getPeriod();

        Double installmentAmount = totalAmount / period;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period);
        ContractStatus status = ContractStatus.ACTIVE;

        List<InstallmentDomain> installments = createInstallments(period, installmentAmount);

        ContractDomain domain = createObject(
                customerId,
                totalAmount,
                period,
                installmentAmount,
                startDate,
                endDate,
                status,
                installments
        );

        return contractGateway.update(domain);
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
            Double totalAmount,
            Integer period,
            Double installmentAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            ContractStatus status,
            List<InstallmentDomain> installments
    ) {
        return ContractDomain.builder()
                .customerId(customerId)
                .totalAmount(totalAmount)
                .period(period)
                .installmentAmount(installmentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .installments(installments)
                .build();
    }
}
