package com.picpay.finsys.core.usecase.impl;

import com.picpay.finsys.core.domain.ContractDomain;
import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.domain.enumeration.InstallmentStatus;
import com.picpay.finsys.core.gateway.ContractGateway;
import com.picpay.finsys.core.usecase.InstallmentsPaymentVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;


@Service
@RequiredArgsConstructor
public class InstallmentsPaymentVerificationImpl implements InstallmentsPaymentVerification {
    private final ContractGateway contractGateway;

    @Value("${finsys.interest-rate.monthly}")
    Double monthlyInterestRate;
    private final DecimalFormat df = new DecimalFormat("#.00");

    public void execute() {
        List<ContractDomain> ids = contractGateway.findAllIds();

        for (ContractDomain id : ids) {
            ContractDomain contract = contractGateway.findById(id.getId());
            ContractDomain newContract = updateContract(contract);
            Long newDaysOverdue = getDaysOverdue(contract.getInstallments());
            newContract.setDaysOverdue(newDaysOverdue);
            contractGateway.update(newContract);
        }
    }

    private ContractDomain updateContract(ContractDomain contract) {
        List<InstallmentDomain> installments = contract.getInstallments();
        for(int i = 0; i < installments.size(); i++) {
            InstallmentDomain deleteInstallment = installments.get(i);
            InstallmentDomain installment = deleteInstallment;
            int installmentIndex = Integer.parseInt(installment.getId()) - 1;
            if (installment.getStatus() == InstallmentStatus.PAID && installment.getDaysOverdue() >= 0) {
                continue;
            }

            installment.setDaysOverdue(installment.getDaysOverdue() + 1);

            if (installment.getDaysOverdue() > 0) {
                installment.setStatus(InstallmentStatus.OVERDUE);
                Double newInstallmentAmount = installment.getAmount() * (1 + ((monthlyInterestRate / 100) / 30) * installment.getDaysOverdue());
                String formattedInstallmentAmount = this.df.format(newInstallmentAmount).replace(',', '.');
                Double installmentAMount = Double.parseDouble(formattedInstallmentAmount);
                installment.setAmount(installmentAMount);
            }

            installments.remove(deleteInstallment);
            installments.add(installmentIndex, installment);
        }

        contract.setInstallments(installments);

        return contract;
    }

    private Long getDaysOverdue(List<InstallmentDomain> installments) {
        Long daysOverdue = 0L;
        for (InstallmentDomain installment : installments) {
            if (installment.getStatus() != InstallmentStatus.PAID) {
                daysOverdue = installment.getDaysOverdue();
                break;
            }
        }

        return daysOverdue;
    }
}
