package com.picpay.finsys.core.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("the requested credentials are wrong");
    }
}
