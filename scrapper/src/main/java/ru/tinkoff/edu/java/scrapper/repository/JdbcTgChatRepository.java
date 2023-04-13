package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Chat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JdbcLinkDao jdbcLinkDao;

    public Long addChat(Chat chat) {
        String query = "insert into chat values(?, ?, ?) returning id;";

        chat.getTrackedLinksId().forEach(jdbcLinkDao::addLink);
        return jdbcTemplate.queryForObject(query, Long.class,
                chat.getId(), chat.getTgChatId(), chat.getTrackedLinksId());
    }

    public List<Chat> findAllChats() {
        String query = "select * from chat;";
        return jdbcTemplate.queryForList(query, Chat.class);
    }

    public Chat findChatById(Long tgChatId) {
        String query = "select * from chat where tgChatId=?;";
        return jdbcTemplate.queryForObject(query, Chat.class, tgChatId);
    }

    public Chat removeChatById(Long tgChatId) {
        String query = "delete from chat where tgChatId=?;";
        return jdbcTemplate.queryForObject(query, Chat.class, tgChatId);
    }
}
