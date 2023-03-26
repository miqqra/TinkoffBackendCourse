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

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IncorrectDataException.class,
            ExistingDataException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiErrorResponse> handleIncorrectDataException(
            IncorrectDataException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        e.getMessage(),
                        HttpStatusCode.valueOf(400).toString(),
                        e.toString(),
                        webRequest.toString(),
                        Arrays.stream(e.getStackTrace())
                                .map(StackTraceElement::toString)
                                .collect(Collectors.toList())),
                HttpStatusCode.valueOf(400)
        );
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<ApiErrorResponse> handleDataNotFoundException(
            DataNotFoundException e, WebRequest webRequest){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        e.getMessage(),
                        HttpStatusCode.valueOf(404).toString(),
                        e.toString(),
                        webRequest.toString(),
                        Arrays.stream(e.getStackTrace())
                                .map(StackTraceElement::toString)
                                .collect(Collectors.toList())),
                HttpStatusCode.valueOf(404)
        );
    }
}
