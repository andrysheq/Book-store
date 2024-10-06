package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalParameterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5545805078598961196L;

    public IllegalParameterException(String exception) {
        super(exception);
    }

}
