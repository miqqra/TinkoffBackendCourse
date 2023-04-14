package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.repository.dto.FindChatResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);
    private final RowMapper<FindChatResponse> findChatResponseDataClassRowMapper =
            new DataClassRowMapper<>(FindChatResponse.class);
    private final JdbcLinkDao jdbcLinkDao;

    public Long addChat(Chat chat) {
        String query = "insert into chat(tgchatid, trackedlink) values(:tgchatid, :trackedlink) returning *";
        Optional<Chat> optionalChat = findChatByTgChatId(chat.getTgChatId());
        return optionalChat
                .map(value -> addToExistingChat(value, chat, query))
                .orElseGet(() -> addToNewChat(chat, query));
    }

    public List<Chat> findAllChats() {
        String query = "select chat.id, tgchatid, trackedlink, url from chat, link where trackedlink = link.id";
        List<FindChatResponse> findAllChatsResponses =
                jdbcTemplate.query(query, Map.of(), findChatResponseDataClassRowMapper);
        return FindChatResponse.mapToChat(findAllChatsResponses);
    }

    public Optional<Chat> findChatByTgChatId(Long tgChatId) {
        String query = "select chat.id, tgchatid, trackedlink, url from chat, link " +
                "where tgchatid = :tgchatid and trackedlink = link.id";
        List<FindChatResponse> findAllChatsResponses =
                jdbcTemplate.query(query, Map.of("tgchatid", tgChatId), findChatResponseDataClassRowMapper);
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        FindChatResponse.mapToChat(findAllChatsResponses)
                )
        );
    }

    public Optional<Long> removeChatByTgChatId(Long tgChatId) {
        String query = "delete from chat where tgchatid = :tgchatid returning *";
        Optional<Chat> chat = findChatByTgChatId(tgChatId);
        jdbcTemplate.query(query, Map.of("tgchatid", tgChatId), rowMapper);
        return chat.isPresent() ? Optional.of(tgChatId) : Optional.empty();
    }

    private Long addToExistingChat(Chat existingChat, Chat newChat, String query) {
        newChat.getTrackedLinksId().forEach(link -> {
            if (!existingChat.getTrackedLinksId().contains(link)) {
                existingChat.addTrackedLink(link);
                Link createdLink = jdbcLinkDao.addLink(link);
                jdbcTemplate.query(
                        query,
                        Map.of("tgchatid", newChat.getTgChatId(), "trackedlink", createdLink.getId()),
                        rowMapper
                );
            }
        });
        return existingChat.getTgChatId();
    }

    private Long addToNewChat(Chat newChat, String query) {
        if (newChat.getTrackedLinksId().isEmpty()) {
            return null;
        } else {
            newChat.getTrackedLinksId().forEach(link -> {
                Link createdLink = jdbcLinkDao.addLink(link);
                jdbcTemplate.query(
                        query,
                        Map.of("tgchatid", newChat.getTgChatId(), "trackedlink", createdLink.getId()),
                        rowMapper
                );
            });
        }
        return newChat.getTgChatId();
    }
}
