package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class BalanceException extends ValidationException {

    public BalanceException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
