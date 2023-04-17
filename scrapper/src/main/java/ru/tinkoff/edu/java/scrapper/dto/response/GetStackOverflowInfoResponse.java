package ru.tinkoff.edu.java.scrapper.dto.response;

public record GetStackOverflowInfoResponse(StackOverflowItems[] items){
    public record StackOverflowItems(long last_activity_date){}
}
