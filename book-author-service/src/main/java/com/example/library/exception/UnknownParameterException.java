package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnknownParameterException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2867424041158713954L;

    public UnknownParameterException(String exception) {
        super(exception);
    }

}
