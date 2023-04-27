package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GetStackoverflowAnswerResponse(StackOverflowAnswers[] answers) {
    public record StackOverflowAnswers(StackOverflowAnswerOwner owner, OffsetDateTime last_activity_date){}

    public record StackOverflowAnswerOwner(String display_name, Long reputation){}
}
