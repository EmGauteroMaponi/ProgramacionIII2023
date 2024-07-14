package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class SueldoException extends ValidationException{

    public SueldoException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
