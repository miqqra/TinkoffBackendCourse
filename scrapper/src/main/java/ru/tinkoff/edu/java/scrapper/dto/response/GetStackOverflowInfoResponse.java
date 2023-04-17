package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GetStackOverflowInfoResponse(StackOverflowItems[] items){
    public record StackOverflowItems(OffsetDateTime last_activity_date){}
}
