package org.ejemplo.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class FacturaException extends Exception {
    private HttpStatus statusCode;
    private String causa;

    public FacturaException(HttpStatus status, String message, String cause) {
        super(message);
        this.causa = cause;
        this.statusCode = status;
    }
}
