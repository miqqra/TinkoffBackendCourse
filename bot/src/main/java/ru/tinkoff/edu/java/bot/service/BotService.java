package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class BotService {
    private final BotClient botClient;

    public void sendUpdate(LinkUpdate sendUpdateRequest) {
        if (sendUpdateRequest.getId() == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
    }

    public String showCommandsList() {
        return botClient.showCommandsList();
    }

    public ListLinksResponse showTrackedLinks(Long id) {
        return botClient.showTrackedLinks(id).block();
    }

    public String registrateUser(Long id) {
        try {
            return botClient
                    .registrateUser(id)
                    .block()
                    .getBody()
                    .toString();
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
            return "Некорректная ссылка";
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
        } catch (WebClientResponseException e){
            return "Неккоректная ссылка";
        }
        return "Ссылка больше не отслеживается";
    }
}
