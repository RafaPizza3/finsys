package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class InvalidDocumentException extends BadRequestException {
    public InvalidDocumentException() {
        super("the requested document is invalid");
    }
}
