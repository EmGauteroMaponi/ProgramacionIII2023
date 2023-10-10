package org.ejemplo.exceptions;


import org.springframework.http.HttpStatus;

public class UserException extends ValidationException {

    public UserException(HttpStatus status, String message, String cause) {
        super(status, message, cause);
    }
}
