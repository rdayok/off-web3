package com.rdi.offweb3.exceptions;

import org.springframework.http.HttpStatus;

public class OtpCodeIsNotValidException extends OfWeb3Exception {
    public OtpCodeIsNotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
