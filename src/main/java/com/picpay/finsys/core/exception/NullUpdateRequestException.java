package com.picpay.finsys.core.exception;

import org.apache.coyote.BadRequestException;

public class NullUpdateRequestException extends BadRequestException {
    public NullUpdateRequestException() {
        super("at least 1 value must be requested");
    }
}
