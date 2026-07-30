package com.picpay.finsys.core.usecase.impl.validation;

import com.picpay.finsys.core.exception.InvalidDocumentException;
import org.springframework.stereotype.Component;

@Component
public class CustomerDocumentValidation {
    public void validate(String document) throws InvalidDocumentException {
        try {
            long documentNumber = Long.parseLong(document);
            if (document.length() != 11) {
                throw new InvalidDocumentException();
            }
        } catch (Exception e) {
            throw new InvalidDocumentException();
        }
    }
}
