package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final BotClient botClient;

    public String showCommandsList() {
        return botClient.showCommandsList();
    }

    public ListLinksResponse showTrackedLinks(Long id) {
        return botClient.showTrackedLinks(id).block();
    }

    public String registrateUser(Long id) {
        try {
            return Objects.requireNonNull(Objects.requireNonNull(
                    botClient
                            .registrateUser(id)
                            .block()));
        } catch (WebClientResponseException e) {
            return "Пользователь уже зарегистрирован";
        }
    }

    public String startTrackingLink(String newLink, Long userId) {
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

    public String stopTrackingLink(String link, Long userId) {
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
            return "Неккоректная ссылка";
        }
        return "Ссылка больше не отслеживается";
    }
}
