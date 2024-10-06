package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7594567264275316144L;

    public ApplicationException(String exception) {
        super(exception);
    }

}
