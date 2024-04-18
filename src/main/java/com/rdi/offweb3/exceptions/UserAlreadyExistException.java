package com.rdi.offweb3.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends OfWeb3Exception {
    public UserAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

}
