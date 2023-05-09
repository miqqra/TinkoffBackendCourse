package ru.tinkoff.edu.java.bot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    /**
     * Error description.
     */
    private String description;
    /**
     * Error http code.
     */
    private String code;
    /**
     * Exception name.
     */
    private String exceptionName;
    /**
     * Exception message.
     */
    private String exceptionMessage;
    /**
     * Stacktrace.
     */
    private List<String> stacktrace;
}
