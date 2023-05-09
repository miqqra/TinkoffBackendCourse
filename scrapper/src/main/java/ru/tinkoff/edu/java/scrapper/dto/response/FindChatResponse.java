package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.mapper.FindChatResponseLinkMapper;

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
    private OffsetDateTime lastCommitDate;
    private OffsetDateTime lastAnswerDate;

    public static List<Chat> mapToChat(List<FindChatResponse> list) {
        HashMap<Long, Chat> chats = new HashMap<>();
        Long tgChatId;
        for (FindChatResponse findAllChatsResponse : list) {
            tgChatId = findAllChatsResponse.getTgChatId();
            if (chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() != null) {
                Chat oldChat = chats.get(tgChatId);
                oldChat.addTrackedLink(FindChatResponseLinkMapper.FindChatResponseToLink(findAllChatsResponse));
                chats.put(tgChatId, oldChat);
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() != null) {
                Chat newChat = createChatObject(findAllChatsResponse);
                newChat.addTrackedLink(FindChatResponseLinkMapper.FindChatResponseToLink(findAllChatsResponse));
                chats.put(tgChatId, newChat);
            } else if (!chats.containsKey(tgChatId) && findAllChatsResponse.getTrackedLink() == null) {
                chats.put(tgChatId, createChatObject(findAllChatsResponse));
            }
        }
        return chats.values().stream().toList();
    }

    private static Chat createChatObject(FindChatResponse findAllChatsResponse) {
        Chat newChat = new Chat();
        newChat.setId(findAllChatsResponse.getId());
        newChat.setTgChatId(findAllChatsResponse.getTgChatId());
        return newChat;
    }
}
