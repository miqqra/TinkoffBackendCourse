package ru.tinkoff.edu.java.scrapper.jdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
public class JdbcChatTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        String SQLRequest = "select * from chat;";
        Chat chat = null;
        chat = new Chat(1L, 1L, List.of(
                new Link(1L, "github.com"),
                new Link(2L, "stackoverflow.com")));

        chatRepository.addChat(chat);
        try (
                Connection connection = DriverManager.getConnection(
                        DB_CONTAINER.getJdbcUrl(),
                        DB_CONTAINER.getUsername(),
                        DB_CONTAINER.getPassword());
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQLRequest)
        ) {

            Chat resultChat = new Chat();
            while (resultSet.next()) {
                resultChat.setId(resultSet.getLong("id"));
                resultChat.setTgChatId(resultSet.getLong("tgChatId"));
                resultChat.addTrackedLink(resultSet.getObject("trackedlink", Link.class));
            }

            assertThat(resultChat, is(notNullValue()));
            assertThat(resultChat.getId(), is(equalTo(1L)));
            assertThat(resultChat.getTgChatId(), is(equalTo(1L)));
            assertThat(resultChat.getTrackedLinksId(), is(not(empty())));
            assertThat(resultChat.getTrackedLinksId().size(), is(equalTo(2)));
            assertThat(resultChat.getTrackedLinksId().get(0).getId(), is(equalTo(1L)));
            assertThat(resultChat.getTrackedLinksId().get(0).getUrl(), is(equalTo("github.com")));
            assertThat(resultChat.getTrackedLinksId().get(1).getId(), is(equalTo(2L)));
            assertThat(resultChat.getTrackedLinksId().get(1).getUrl(), is(equalTo("stackoverflow.com")));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
    }

    @Test
    void findAllTest() {

    }
}
