package ru.tinkoff.edu.java.scrapper.mapper;

import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.FindChatResponse;

public class FindChatResponseLinkMapper {
    public static Link FindChatResponseToLink(FindChatResponse findChatResponse){
        return new Link(
                findChatResponse.getTrackedLink(),
                findChatResponse.getUrl(),
                findChatResponse.getLastUpdated(),
                findChatResponse.getLastChecked(),
                findChatResponse.getLastCheckedWhenWasUpdated(),
                findChatResponse.getLastCommitDate(),
                findChatResponse.getLastAnswerDate());
    }
}
