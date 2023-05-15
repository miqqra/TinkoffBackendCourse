package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.FindChatResponse;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);
    private final RowMapper<FindChatResponse> findChatResponseDataClassRowMapper =
            new DataClassRowMapper<>(FindChatResponse.class);
    private final JdbcLinkDao jdbcLinkDao;

    @Transactional
    public Long addChat(Long tgChatId) {
        String query = "insert into chat(tgchatid) values(:tgchatid) returning *";
        jdbcTemplate.query(
                query,
                Map.of("tgchatid", tgChatId),
                rowMapper
        );
        return tgChatId;
    }

    @Transactional
    public Long addChat(Chat chat) {
        String query = "insert into chat(tgchatid, trackedlink) values(:tgchatid, :trackedlink) returning *";
        Optional<Chat> optionalChat = findChatByTgChatId(chat.getTgChatId());
        return optionalChat
                .map(value -> addToExistingChat(value, chat, query))
                .orElseGet(() -> addToNewChat(chat, query));
    }

    @Transactional
    public Link addLinkToChat(Long tgChatId, String url) {
        String query = "insert into chat(tgchatid, trackedlink) values(:tgchatid, :trackedlink) returning *";
        Optional<Link> optionalLink = jdbcLinkDao.findLinkByUrl(url);
        if (optionalLink.isPresent()) {
            jdbcTemplate.query(query,
                    Map.of("tgchatid", tgChatId, "trackedlink", optionalLink.get().getId()),
                    rowMapper);
            return optionalLink.get();
        } else {
            Link createdLink = jdbcLinkDao.addLink(new Link(url));
            jdbcTemplate.query(query,
                    Map.of("tgchatid", tgChatId, "trackedlink", createdLink.getId()),
                    rowMapper);
            return createdLink;
        }
    }

    public List<Chat> findAllChats() {
        String query = """
                select chat.id, tgchatid, trackedlink, url, last_updated, last_checked,
                       last_checked_when_was_updated, last_commit_date, last_answer_date
                from chat, link where trackedlink = link.id
                union select chat.id, tgchatid, trackedlink, null, null, null,
                             null, null, null
                from chat, link where trackedlink is null
                """;
        List<FindChatResponse> findAllChatsResponses =
                jdbcTemplate.query(query, Map.of(), findChatResponseDataClassRowMapper);
        return FindChatResponse.mapToChat(findAllChatsResponses);
    }

    public Optional<Chat> findChatByTgChatId(Long tgChatId) {
        String query = """
                select chat.id, tgchatid, trackedlink, url, last_updated, last_checked,
                       last_checked_when_was_updated, last_commit_date, last_answer_date
                from chat, link where tgchatid = :tgchatid and trackedlink = link.id
                union select chat.id, tgchatid, trackedlink, null, null, null,
                             null, null, null
                from chat, link where tgchatid = :tgchatid and trackedlink is null
                """;
        List<FindChatResponse> findAllChatsResponses =
                jdbcTemplate.query(query, Map.of("tgchatid", tgChatId), findChatResponseDataClassRowMapper);
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        FindChatResponse.mapToChat(findAllChatsResponses)
                )
        );
    }

    @Transactional
    public Optional<Long> removeChatByTgChatId(Long tgChatId) {
        String query = "delete from chat where tgchatid = :tgchatid returning *";
        Optional<Chat> chat = findChatByTgChatId(tgChatId);
        jdbcTemplate.query(query, Map.of("tgchatid", tgChatId), rowMapper);
        return chat.isPresent() ? Optional.of(tgChatId) : Optional.empty();
    }

    @Transactional
    public Optional<Link> removeChatByUrl(Chat chat, Link link) {
        if (chat.getTrackedLinksId().stream().noneMatch(chatLink -> chatLink.getUrl().equals(link.getUrl())))
            return Optional.empty();
        String query = """
                delete from chat where tgchatid = :tgchatid
                and trackedlink = :trackedlink returning trackedlink
                """;
        jdbcTemplate.query(query,
                Map.of("tgchatid", chat.getTgChatId(), "trackedlink", link.getId()),
                rowMapper);
        return Optional.of(link);
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
            String queryWithoutTrackedLink = "insert into chat(tgchatid) values(:tgchatid) returning *";
            jdbcTemplate.query(
                    queryWithoutTrackedLink,
                    Map.of("tgchatid", newChat.getTgChatId()),
                    rowMapper
            );
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
