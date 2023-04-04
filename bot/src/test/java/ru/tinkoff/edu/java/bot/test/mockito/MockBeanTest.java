package ru.tinkoff.edu.java.bot.test.mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.BotService;
import ru.tinkoff.edu.java.bot.test.client.TestBotClient;

@SpringBootTest(classes = {BotService.class})
public class MockBeanTest {
    @MockBean
    private TestBotClient botClient;

    @Test
    void showTrackedLinks(Long id) {

        ListLinksResponse listLinksResponse = botClient.showTrackedLinks(id);

        assertThat(listLinksResponse, is(notNullValue()));
        assertThat(listLinksResponse.links(), is(notNullValue()));
        assertThat(listLinksResponse.links(), hasSize(3));
        assertThat(listLinksResponse.size(), equalTo(3));

        var user1 = listLinksResponse.links().get(0);
        assertThat(user1, is(notNullValue()));
        assertThat(user1.url(), is(notNullValue()));
        assertThat(user1.url().toString(), equalTo("github.com/miqqra/OOP"));
        assertThat(user1.id(), equalTo(1L));

        var user2 = listLinksResponse.links().get(1);
        assertThat(user2, is(notNullValue()));
        assertThat(user2.url(), is(notNullValue()));
        assertThat(user2.url().toString(), equalTo("github.com/miqqra/DepersonalisationService"));
        assertThat(user2.id(), equalTo(1L));

        var user3 = listLinksResponse.links().get(2);
        assertThat(user3, is(notNullValue()));
        assertThat(user3.url(), is(notNullValue()));
        assertThat(user3.url().toString(), equalTo("stackoverflow.com/questions/49733733"));
        assertThat(user3.id(), equalTo(1L));
    }
}
