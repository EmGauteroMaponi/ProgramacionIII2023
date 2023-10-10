package org.ejemplo.exceptions;

import org.springframework.http.HttpStatus;

public class ClientException extends ValidationException {

    public ClientException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
