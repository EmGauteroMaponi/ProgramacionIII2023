package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class DetalleFacturaException extends ValidationException {

    public DetalleFacturaException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
