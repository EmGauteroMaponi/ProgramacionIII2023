package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class FacturaException extends ValidationException {

    public FacturaException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
