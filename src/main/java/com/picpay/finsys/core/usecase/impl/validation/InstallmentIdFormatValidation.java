package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.InstallmentIdFormatNotAllowedException;
import org.springframework.stereotype.Component;

@Component
public class InstallmentIdFormatValidation {
    public void validate(String installmentId) {
        try {
            int intId = Integer.parseInt(installmentId);

            if(intId < 0) {
                throw new InstallmentIdFormatNotAllowedException(installmentId);
            }
        } catch (Exception e) {
            throw new InstallmentIdFormatNotAllowedException(installmentId);
        }
    }
}
