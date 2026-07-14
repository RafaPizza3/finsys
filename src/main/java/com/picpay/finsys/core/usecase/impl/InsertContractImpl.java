package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.InsertContractUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertContractImpl implements InsertContractUseCase {
    private final ContractGateway contractGateway;

    @Override
    public ContractDomain execute(ContractDomain contract) {
        String customerId = contract.getCustomerId();
        Double totalAmount = contract.getTotalAmount();
        Integer period = contract.getPeriod();

        Double installmentAmount = totalAmount / period;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(period);
        ContractStatus staus = ContractStatus.ACTIVE;
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

        ContractDomain domain = ContractDomain.builder()
                .customerId(customerId)
                .totalAmount(totalAmount)
                .period(period)
                .installmentAmount(installmentAmount)
                .startDate(startDate)
                .endDate(endDate)
                .status(staus)
                .installments(installments)
                .build();

        return contractGateway.insert(domain);
    }
}
