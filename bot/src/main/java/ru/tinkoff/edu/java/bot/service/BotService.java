package ru.tinkoff.edu.java.bot.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

@Service
@RequiredArgsConstructor
public class BotService {
    private final BotClient botClient;

    public String showCommandsList() {
        return botClient.showCommandsList();
    }

    public ListLinksResponse showTrackedLinks(final Long id) {
        return botClient.showTrackedLinks(id).block();
    }

    public String registrateUser(final Long id) {
        try {
            return Objects.requireNonNull(Objects.requireNonNull(
                    botClient
                            .registrateUser(id)
                            .block()));
        } catch (WebClientResponseException e) {
            return "Пользователь уже зарегистрирован";
        }
    }

    public String startTrackingLink(
            final String newLink, final Long userId
    ) {
        URI newURI;
        try {
            newURI = new URI(newLink);
        } catch (URISyntaxException e) {
            return "Некорректная ссылка";
        }

        try {
            botClient
                    .startTrackingLink(newURI, userId)
                    .block();
        } catch (WebClientResponseException e) {
            return e.getResponseBodyAsString();
        }
        return "Ссылка добавлена";
    }

    public String stopTrackingLink(
            final String link, final Long userId
    ) {
        URI uri;
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            return "Некорректная ссылка";
        }

        try {
            botClient
                    .stopTrackingLink(uri, userId)
                    .block();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            return "Ошибка сервера";
        }
        return "Ссылка больше не отслеживается";
    }
}
