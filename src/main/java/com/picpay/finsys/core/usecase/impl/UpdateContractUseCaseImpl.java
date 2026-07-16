package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.CustomerDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.ContractStatus;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.CustomerNotFoundException;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.gateway.CustomerGateway;
import com.picpay.finsys.core.usecase.UpdateContractUseCase;
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

    @Override
    public ContractDomain execute(String id, ContractDomain contract) throws BadRequestException {
        verifyRequest(contract);

        Double interestRate = 4.0;

        ContractDomain bdContract = contractGateway.findById(id);
        if (bdContract == null) {
            throw new ContractNotFoundException(id);
        }

        verifyValue(contract.getValue(), bdContract.getValue());
        verifyPeriod(contract.getPeriod(), bdContract.getPeriod());

        Double value = 0.0;
        Integer period = 0;

        if (contract.getCustomerId() != null) {
            verifyCustomer(contract.getId());
            bdContract.setCustomerId(contract.getCustomerId());
        }

        if (contract.getPeriod() != null) {
            bdContract.setPeriod(contract.getPeriod());
        }

        if (contract.getValue() != null) {
            bdContract.setInstallments(
                    adjustInstallmentsByValue(
                            contract.getValue(),
                            bdContract.getPeriod(),
                            interestRate,
                            bdContract.getInstallments()
                    )
            );

            bdContract.setValue(contract.getValue());
            bdContract.setTotalAmount(calcNewTotalAmount(contract.getValue(), bdContract.getPeriod(), interestRate));
            bdContract.setInstallmentAmount(calcNewInstallmentAmount(contract.getValue(), bdContract.getPeriod(), interestRate));
            bdContract.setEndDate(
                    bdContract.getInstallments().getLast().getDueDate()
            );
        }

        ContractDomain domain = createObject(
                bdContract.getId(),
                bdContract.getCustomerId(),
                bdContract.getValue(),
                bdContract.getTotalAmount(),
                interestRate,
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
                .id(id)
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

    private List<InstallmentDomain> adjustInstallmentsByValue(
            Double value,
            Integer period,
            Double interestRate,
            List<InstallmentDomain> installments
    ) {
        Double newInstallmentValue = calcNewInstallmentAmount(value, period, interestRate);

        LocalDateTime now = LocalDateTime.now();

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
        return (value / period) + (value * (interestRate / 100));
    }

    private Double calcNewTotalAmount(Double value, Integer period, Double interestRate) {
        Double newInstallmentAmount = calcNewInstallmentAmount(value, period, interestRate);

        return newInstallmentAmount * period;
    }

    private void verifyRequest(ContractDomain request) throws BadRequestException {
        if (request.getCustomerId() == null && request.getValue() == null && request.getPeriod() == null) {
            throw new BadRequestException("at least 1 value must be requested");
        }
    }

    private void verifyValue(Double requestValue, Double originalValue) throws BadRequestException {
        if(requestValue < originalValue) {
            throw new BadRequestException("new contract value must be bigger or equals original value");
        }
    }

    private void verifyPeriod(Integer requestPeriod, Integer originalPeriod) throws BadRequestException {
        if(requestPeriod < originalPeriod) {
            throw new BadRequestException("new contract value must be bigger or equals original value");
        }
    }

    private void verifyCustomer(String customerId) throws CustomerNotFoundException {
        CustomerDomain customer = customerGateway.findById(customerId);

        if(customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}
