package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record GetStackoverflowAnswerResponse(List<StackOverflowAnswers> answers) {
    public record StackOverflowAnswers(StackOverflowAnswerOwner owner, OffsetDateTime last_activity_date){}

    public record StackOverflowAnswerOwner(String display_name, Long reputation){}
}
