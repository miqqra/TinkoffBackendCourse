package ru.tinkoff.edu.java.scrapper.jdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JdbcChatTest extends IntegrationEnvironment {
    @Autowired
    private JdbcTgChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    void addChatTest() {
        Link link1 = new Link("github.com");
        Link link2 = new Link("stackoverflow.com");
        Link link3 = new Link("notstackoverflow.com");
        Link link4 = new Link("reallynotstackoverflow.com");

        Chat chat1 = new Chat(1L, 1L, List.of());
        Chat chat2 = new Chat(2L, 2L, List.of(link1));
        Chat chat3 = new Chat(3L, 3L, List.of(link1, link2, link3, link4));

        chatRepository.addChat(chat1);
        chatRepository.addChat(chat2);
        chatRepository.addChat(chat3);

        List<Chat> chats = chatRepository.findAllChats();

        assertThat(chats, is(notNullValue()));

        assertThat(chats.size(), equalTo(2));

        assertThat(chats.get(0).getTgChatId(), equalTo(2L));
        assertThat(chats.get(0).getTrackedLinksId().size(), equalTo(1));
        assertThat(chats.get(0).getTrackedLinksId().get(0).getUrl(), equalTo(link1.getUrl()));

        assertThat(chats.get(1).getTgChatId(), equalTo(3L));
        assertThat(chats.get(1).getTrackedLinksId().size(), equalTo(4));
        assertThat(chats.get(1).getTrackedLinksId().get(0).getUrl(), equalTo(link1.getUrl()));
        assertThat(chats.get(1).getTrackedLinksId().get(1).getUrl(), equalTo(link2.getUrl()));
        assertThat(chats.get(1).getTrackedLinksId().get(2).getUrl(), equalTo(link3.getUrl()));
        assertThat(chats.get(1).getTrackedLinksId().get(3).getUrl(), equalTo(link4.getUrl()));
    }

    @Test
    @Transactional
    @Rollback
    void findChatByIdTest() {
        Link link1 = new Link("github.com");
        Link link2 = new Link("stackoverflow.com");
        Link link3 = new Link("notstackoverflow.com");
        Link link4 = new Link("reallynotstackoverflow.com");

        Chat chat1 = new Chat(1L, List.of());
        Chat chat2 = new Chat(2L, List.of(link1));
        Chat chat3 = new Chat(3L, List.of(link1, link2, link3, link4));

        chatRepository.addChat(chat1);
        chatRepository.addChat(chat2);
        chatRepository.addChat(chat3);

        Optional<Chat> chat = chatRepository.findChatByTgChatId(2L);
        Optional<Chat> chat4 = chatRepository.findChatByTgChatId(5L);

        assertThat(chat, is(not(Optional.empty())));
        assertThat(chat.get().getTrackedLinksId().size(), equalTo(1));
        assertThat(chat.get().getTrackedLinksId().get(0).getUrl(), equalTo(link1.getUrl()));

        assertThat(chat4, is(Optional.empty()));
    }

    @Test
    @Transactional
    @Rollback
    void removeChatTest() {
        Link link1 = new Link("github.com");
        Link link2 = new Link("stackoverflow.com");
        Link link3 = new Link("notstackoverflow.com");
        Link link4 = new Link("reallynotstackoverflow.com");

        Chat chat1 = new Chat(1L, List.of());
        Chat chat2 = new Chat(2L, List.of(link1));
        Chat chat3 = new Chat(3L, List.of(link1, link2, link3, link4));

        Long addedChatId1 = chatRepository.addChat(chat1);
        Long addedChatId2 = chatRepository.addChat(chat2);
        Long addedChatId3 = chatRepository.addChat(chat3);

        Optional<Long> removedChatId = chatRepository.removeChatByTgChatId(3L);
        Optional<Long> nonExistingRemovedChatId = chatRepository.removeChatByTgChatId(6L);
        List<Chat> chats = chatRepository.findAllChats();

        assertThat(addedChatId1, is(nullValue()));
        assertThat(addedChatId2, equalTo(2L));
        assertThat(addedChatId3, equalTo(3L));

        assertThat(chats, is(notNullValue()));
        assertThat(chats, is(not(emptyIterable())));

        assertThat(chats, is(notNullValue()));
        assertThat(chats.size(), equalTo(1));
        assertThat(chats.get(0).getTgChatId(), equalTo(chat2.getTgChatId()));
        assertThat(chats.get(0).getTrackedLinksId().size(), equalTo(chat2.getTrackedLinksId().size()));

        assertThat(removedChatId, is(not(Optional.empty())));
        assertThat(removedChatId.get(), equalTo(3L));

        assertThat(nonExistingRemovedChatId, is(Optional.empty()));
    }
}
