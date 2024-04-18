package com.rdi.offweb3.exceptions;

import org.springframework.http.HttpStatus;

public class SendEmailNotSuccessfulException extends OfWeb3Exception {
    public SendEmailNotSuccessfulException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
