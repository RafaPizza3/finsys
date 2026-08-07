package com.picpay.finsys.core.usecase;

import com.picpay.finsys.core.domain.InstallmentDomain;
import com.picpay.finsys.core.exception.CanceledInstallmentException;
import com.picpay.finsys.core.exception.ContractNotFoundException;
import com.picpay.finsys.core.exception.ExceededInstallmentAmountInPaymentException;
import com.picpay.finsys.core.exception.InstallmentNotFoundException;
import com.picpay.finsys.core.exception.InstallmentPaymentPriorityException;
import com.picpay.finsys.core.exception.PaidInstallmentException;
import java.time.LocalDateTime;

public interface InstallmentPaymentUseCase {
    InstallmentDomain execute(String contractId, String installmentId, Double paymentAmount, LocalDateTime messageDate) throws ContractNotFoundException, InstallmentNotFoundException, ExceededInstallmentAmountInPaymentException, InstallmentPaymentPriorityException, PaidInstallmentException, CanceledInstallmentException;
}
