package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.CommandHandler;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockBeanTest {
    @Mock
    private CommandHandler botService;
    private UserMessageProcessor userMessageProcessor;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userMessageProcessor = new UserMessageProcessor(
                List.of(new ListCommand(botService)));
    }

    @AfterEach
    void closeService() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testListCommand() throws URISyntaxException {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().text()).thenReturn("/list");
        Mockito.when(update.message().chat().id()).thenReturn(1L);
        Mockito.when(botService.showTrackedLinks(anyLong())).thenReturn(
                new ListLinksResponse(
                        List.of(
                                new LinkResponse(1L, new URI("https://github.com/miqqra/OOP")),
                                new LinkResponse(2L, new URI("https://stackoverflow.com/questions/49733733"))
                        ), 2
                ));

        SendMessage sendMessage = userMessageProcessor.process(update);

        assertThat(sendMessage, is(notNullValue()));
        assertThat(sendMessage.getParameters().get("text"), equalTo(
                "https://github.com/miqqra/OOP\nhttps://stackoverflow.com/questions/49733733\n"
        ));
        assertThat(sendMessage.getParameters().get("chat_id"), equalTo(1L));
    }

    @Test
    void testEmptyLinksList() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().text()).thenReturn("/list");
        Mockito.when(update.message().chat().id()).thenReturn(1L);
        Mockito.when(botService.showTrackedLinks(anyLong()))
                .thenReturn(new ListLinksResponse(List.of(), 0));

        SendMessage sendMessage = userMessageProcessor.process(update);

        assertThat(sendMessage, is(notNullValue()));
        assertThat(sendMessage.getParameters().get("text"), equalTo("Нет отслеживаемых ссылок"));
        assertThat(sendMessage.getParameters().get("chat_id"), equalTo(1L));
    }

    @Test
    void testUnknownCommand() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().chat()).thenReturn(chat);
        Mockito.when(update.message().text()).thenReturn("/unknown");
        Mockito.when(update.message().chat().id()).thenReturn(1L);

        SendMessage sendMessage = userMessageProcessor.process(update);

        assertThat(sendMessage, is(notNullValue()));
        assertThat(sendMessage.getParameters().get("text"), equalTo("Некорректная команда"));
        assertThat(sendMessage.getParameters().get("chat_id"), equalTo(1L));
    }
}
