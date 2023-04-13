package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Chat;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    public Optional<Chat> addChat(Chat chat) {
        String query = "insert into chat values(?, ?, ?)";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                query,
                                rowMapper,
                                chat.getId(), chat.getTgChatId(), chat.getTrackedLinksId()
                        )
                )
        );
    }

    public List<Chat> findAllChats() {
        String query = "select * from chat";
        return jdbcTemplate.query(query, rowMapper);
    }

    public Optional<Chat> findChatById(Long tgChatId) {
        String query = "select * from chat where tgChatId=?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, rowMapper, tgChatId)
                )
        );
    }

    public Optional<Chat> removeChatById(Long tgChatId) {
        String query = "delete from chat where tgChatId=?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                query, rowMapper, tgChatId
                        )
                )
        );
    }
}
