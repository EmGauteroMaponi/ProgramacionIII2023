package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class ProductoException extends ValidationException {

    public ProductoException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
