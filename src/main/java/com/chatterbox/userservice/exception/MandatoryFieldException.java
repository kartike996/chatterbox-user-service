package com.chatterbox.userservice.exception;

public class MandatoryFieldException extends RuntimeException {
    public MandatoryFieldException(String message) {
        super(message);
    }
}
