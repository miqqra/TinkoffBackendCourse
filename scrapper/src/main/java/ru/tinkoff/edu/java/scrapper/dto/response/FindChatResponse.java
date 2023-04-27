package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.mapper.FindChatResponseLinkMapper;

import java.time.OffsetDateTime;
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
    private OffsetDateTime lastUpdated;
    private OffsetDateTime lastChecked;
    private OffsetDateTime lastCheckedWhenWasUpdated;

    public static List<Chat> mapToChat(List<FindChatResponse> list) {
        HashMap<Long, Chat> chats = new HashMap<>();
        Long tgChatId;
        for (FindChatResponse findAllChatsResponse : list) {
            tgChatId = findAllChatsResponse.getTgChatId();
            if (chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() != null) {
                chats.get(tgChatId).addTrackedLink(
                        FindChatResponseLinkMapper.FindChatResponseToLink(findAllChatsResponse)
                );
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() != null) {
                Chat newChat = new Chat();
                newChat.setId(findAllChatsResponse.getId());
                newChat.setTgChatId(tgChatId);
                newChat.addTrackedLink(
                        FindChatResponseLinkMapper.FindChatResponseToLink(findAllChatsResponse)
                );
                chats.put(tgChatId, newChat);
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() == null) {
                Chat newChat = new Chat();
                newChat.setId(findAllChatsResponse.getId());
                newChat.setTgChatId(tgChatId);
                chats.put(tgChatId, newChat);
            }
        }
        return chats.values().stream().toList();
    }
}
