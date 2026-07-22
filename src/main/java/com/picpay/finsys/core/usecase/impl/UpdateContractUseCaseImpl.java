package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
import com.picpay.finsys.core.usecase.impl.validation.ContractNewRequestedAmountValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractPeriodUpdateValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractRequestedAmountValidation;
import com.picpay.finsys.core.usecase.impl.validation.ContractUpdateRequestValidation;
import com.picpay.finsys.core.usecase.impl.validation.CustomerExistenceValidation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateContractUseCaseImpl implements UpdateContractUseCase {
    private final ContractGateway contractGateway;
    private final CustomerGateway customerGateway;

    private final CustomerExistenceValidation customerExistenceValidation;
    private final ContractNewRequestedAmountValidation contractNewRequestedAmountValidation;
    private final ContractUpdateRequestValidation contractUpdateRequestValidation;
    private final ContractPeriodUpdateValidation contractPeriodUpdateValidation;

    Integer percentage = 100;

    @Override
    public ContractDomain execute(String id, ContractDomain contract) throws BadRequestException {
        contractUpdateRequestValidation.validate(contract);

        Double monthlyInterestRate = 4.0;

        ContractDomain bdContract = contractGateway.findById(id);
        if (bdContract == null) {
            throw new ContractNotFoundException(id);
        }

        contractPeriodUpdateValidation.validate(contract.getPeriod(), bdContract.getPeriod());

        Double value = 0.0;
        Integer period = 0;

        if (contract.getCustomerId() != null) {
            customerExistenceValidation.validate(contract.getId());
            bdContract.setCustomerId(contract.getCustomerId());
        }

        if (contract.getPeriod() != null) {
            bdContract.setPeriod(contract.getPeriod());
        }

        if (contract.getRequestedAmount() != null) {
            contractNewRequestedAmountValidation.validate(contract.getRequestedAmount(), bdContract.getRequestedAmount());
            bdContract.setInstallments(
                    adjustInstallmentsByValue(
                            contract.getRequestedAmount(),
                            bdContract.getPeriod(),
                            monthlyInterestRate,
                            bdContract.getInstallments()
                    )
            );

            bdContract.setRequestedAmount(contract.getRequestedAmount());
            bdContract.setTotalAmount(calcNewTotalAmount(contract.getRequestedAmount(), bdContract.getPeriod(), monthlyInterestRate));
            bdContract.setInstallmentAmount(calcNewInstallmentAmount(contract.getRequestedAmount(), bdContract.getPeriod(), monthlyInterestRate));
            bdContract.setEndDate(
                    bdContract.getInstallments().getLast().getDueDate()
            );
        }

        ContractDomain domain = createObject(
                bdContract.getId(),
                bdContract.getCustomerId(),
                bdContract.getRequestedAmount(),
                bdContract.getTotalAmount(),
                monthlyInterestRate,
                bdContract.getPeriod(),
                bdContract.getInstallmentAmount(),
                bdContract.getStartDate(),
                bdContract.getEndDate(),
                bdContract.getStatus(),
                bdContract.getInstallments()
        );

        return contractGateway.update(domain);
    }

    private ContractDomain createObject(
            String id,
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
                .id(id)
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

    private List<InstallmentDomain> adjustInstallmentsByValue(
            Double value,
            Integer period,
            Double monthlyInterestRate,
            List<InstallmentDomain> installments
    ) {
        Double newInstallmentValue = calcNewInstallmentAmount(value, period, monthlyInterestRate);

        String lastId = installments.getLast().getId();
        Double totalPaid = 0.0;
        Integer paidQtd = 0;

        for(int i = 0; i <= installments.size() - 1; i ++) {
            InstallmentDomain installment = installments.get(i);

            if (installment.getStatus() == InstallmentStatus.PAID) {
                totalPaid += installment.getAmount();
                paidQtd += 1;
            } else {
                InstallmentDomain newInstallment = installment;
                newInstallment.setStatus(InstallmentStatus.CANCELED);
                installments.add(i, newInstallment);
                installments.remove(installment);
            }
        }

        Double newAmount = (newInstallmentValue * period) - totalPaid;

        Integer installmentRemainingPeriod = period - paidQtd;

        Double newInstallmentAmount = newAmount / installmentRemainingPeriod;

        for(int i = 0; i <= installmentRemainingPeriod; i ++) {
            Integer actualId = Integer.valueOf(lastId);
            String id = String.valueOf(actualId + i);
            InstallmentDomain installment = InstallmentDomain.builder()
                    .id(id)
                    .amount(newInstallmentAmount)
                    .status(InstallmentStatus.OPEN)
                    .dueDate(LocalDateTime.now().plusMonths(i))
                    .build();

            installments.add(installment);
        }

        return  installments;
    }

    private Double calcNewInstallmentAmount(Double value, Integer period, Double interestRate) {
        return (value / period) + (value * (interestRate / this.percentage));
    }

    private Double calcNewTotalAmount(Double value, Integer period, Double interestRate) {
        Double newInstallmentAmount = calcNewInstallmentAmount(value, period, interestRate);

        return newInstallmentAmount * period;
    }
}
