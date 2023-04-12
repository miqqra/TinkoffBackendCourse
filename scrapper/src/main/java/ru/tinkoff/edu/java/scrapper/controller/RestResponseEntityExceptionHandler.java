package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ExistingDataException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IncorrectDataException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> handleIncorrectDataException(
            IncorrectDataException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatusCode.valueOf(400)
        );
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<String> handleDataNotFoundException(
            DataNotFoundException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatusCode.valueOf(404)
        );
    }

    @ExceptionHandler(value = ExistingDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> handleExistingDataException(
            ExistingDataException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatusCode.valueOf(400)
        );
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ApiErrorResponse> handleAnyOtherException(
            IncorrectDataException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.toString(),
                        webRequest.toString(),
                        null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
