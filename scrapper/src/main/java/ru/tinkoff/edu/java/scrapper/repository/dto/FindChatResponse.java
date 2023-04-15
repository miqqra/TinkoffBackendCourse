package ru.tinkoff.edu.java.scrapper.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindChatResponse {
    private Long id;
    private Long tgChatId;
    private Long trackedLink;
    private String url;

    public static List<Chat> mapToChat(List<FindChatResponse> list) {
        HashMap<Long, Chat> chats = new HashMap<>();
        Long tgChatId;
        for (FindChatResponse findAllChatsResponse : list) {
            tgChatId = findAllChatsResponse.getTgChatId();
            if (chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink()!=null) {
                chats.get(tgChatId).addTrackedLink(
                        new Link(findAllChatsResponse.getTrackedLink(), findAllChatsResponse.getUrl())
                );
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink()!=null){
                Chat newChat = new Chat();
                newChat.setId(findAllChatsResponse.getId());
                newChat.setTgChatId(tgChatId);
                newChat.addTrackedLink(new Link(findAllChatsResponse.getTrackedLink(), findAllChatsResponse.getUrl()));
                chats.put(tgChatId, newChat);
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() == null){
                Chat newChat = new Chat();
                newChat.setId(findAllChatsResponse.getId());
                newChat.setTgChatId(tgChatId);
                chats.put(tgChatId, newChat);
            }
        }
        return chats.values().stream().toList();
    }
}
