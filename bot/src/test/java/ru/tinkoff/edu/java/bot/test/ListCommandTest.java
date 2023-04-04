package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.service.BotService;
import static ru.tinkoff.edu.java.bot.test.TestLinksData.getStabTrackedLinks;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ListCommandTest {

    @MockBean
    private final BotService botService;
    private final String command = "/list";
    private final String description = "Показать список отслеживаемых ссылок";

    Long getUserId(Update update) {
        return 1L;
    }

    String prettyPrint(List<LinkResponse> linkResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        linkResponses
                .forEach(linkResponse -> stringBuilder.append(linkResponse.url()).append("\n"));
        return stringBuilder.toString();
    }

    @Test
    void handleEmptyList(Update update) {
        Mockito.when(botService.showTrackedLinks(getUserId(update)).links()).thenReturn(new ArrayList<>());


        List<LinkResponse> listLinksResponse = botService.showTrackedLinks(getUserId(update)).links();
        String result = listLinksResponse.isEmpty() ? "Нет отслеживаемых ссылок" : prettyPrint(listLinksResponse);

        assertThat(listLinksResponse, is(notNullValue()));
        assertThat(listLinksResponse, is(empty()));
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo("Нет отслеживаемых ссылок"));
    }

    @Test
    void handleNotEmptyList(Update update) {
        Mockito.when(botService.showTrackedLinks(getUserId(update)).links()).thenReturn(getStabTrackedLinks().links());


        List<LinkResponse> listLinksResponse = botService.showTrackedLinks(getUserId(update)).links();
        String result = listLinksResponse.isEmpty() ? "Нет отслеживаемых ссылок" : prettyPrint(listLinksResponse);

        assertThat(listLinksResponse, is(notNullValue()));
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo(
                "github.com/miqqra/OOP\n" +
                        "github.com/miqqra/DepersonalisationService\n" +
                        "stackoverflow.com/questions/49733733"));
    }
}
