package ru.tinkoff.edu.java.bot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.bot.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IncorrectDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiErrorResponse> handleBadRequestException(
            final IncorrectDataException e, final WebRequest webRequest
    ) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.toString(),
                        e.toString(),
                        webRequest.toString(),
                        null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ApiErrorResponse> handleAnyOtherException(
            final IncorrectDataException e, final WebRequest webRequest
    ) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.toString(),
                        webRequest.toString(),
                        null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
