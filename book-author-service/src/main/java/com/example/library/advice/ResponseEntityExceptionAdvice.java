package com.example.library.advice;

import com.example.library.dto.error.ErrorResponse;
import com.example.library.exception.ApplicationException;
import com.example.library.exception.IllegalParameterException;
import com.example.library.exception.RecordNotFoundException;
import com.example.library.exception.UnknownParameterException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@ControllerAdvice
public class ResponseEntityExceptionAdvice extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildErrorResponse(String errorCode,
                                                      String message,
                                                      List<String> details,
                                                      HttpStatus httpStatus,
                                                      Exception ex) {

        log.error(getExMessage(ex));

        ErrorResponse error = ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .details(details)
                .build();

        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class, ApplicationException.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return buildErrorResponse(null, "Внутренняя ошибка сервиса", createDetails(ex), HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        return buildErrorResponse(null, "Запись не найдена", createDetails(ex), HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UnknownParameterException.class)
    public final ResponseEntity<Object> handleUnknownParameterException(UnknownParameterException ex, WebRequest request) {
        return buildErrorResponse(null, "Неизвестный параметр запроса", createDetails(ex), HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(IllegalParameterException.class)
    public final ResponseEntity<Object> handleIllegalParameterException(IllegalParameterException ex, WebRequest request) {
        return buildErrorResponse(null, "Недопустимый параметр запроса", createDetails(ex), HttpStatus.BAD_REQUEST, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {

        List<String> errorDetails = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorDetails.add(String.valueOf(error));
        }

        return buildErrorResponse(null, "Запрос не прошел валидацию", errorDetails, HttpStatus.BAD_REQUEST, ex);
    }

    public static String getExMessage(Throwable throwable) {
        return throwable.getCause() == null ? throwable.getMessage() : throwable.getCause().getMessage();
    }

    public static List<String> createDetails(Exception ex) {
        return Collections.singletonList(ex.getLocalizedMessage());
    }
}

