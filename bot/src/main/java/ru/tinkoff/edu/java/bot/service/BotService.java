package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessor;

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
        return UserMessageProcessor.showAllCommands();
    }

    public ListLinksResponse showTrackedLinks(Long id) {
        String path = "/links";
        ListLinksResponse listLinksResponse = botClient
                .webClient()
                .get()
                .uri(path)
                .header("tgChatId", id.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
        return listLinksResponse;
    }

    public String registrateUser(Long id) {
        String path = "tg-chat/%d".formatted(id);
        Mono<ResponseEntity> response = botClient
                .webClient()
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
        try {
            return response.block().getBody().toString();
        } catch (WebClientResponseException e) {
            return "Пользователь уже зарегистрирован";
        }
    }

    public String startTrackingLink(String newLink, Long userId) {
        String path = "/links";
        try {
            LinkResponse linkResponse = botClient
                    .webClient()
                    .post()
                    .uri(path)
                    .body(BodyInserters.fromValue(new AddLinkRequest(new URI(newLink))))
                    .header("tgChatId", userId.toString())
                    .retrieve()
                    .bodyToMono(LinkResponse.class)
                    .block();
        } catch (URISyntaxException | WebClientResponseException e) {
            return "Некорректная ссылка";
        }
        return "Ссылка добавлена";
    }

    public String stopTrackingLink(String link, Long userId) {
        String path = "/links";
        try {
            LinkResponse linkResponse = botClient
                    .webClient()
                    .delete()
                    .uri(path)
                    .header("removeLinkRequest", (new RemoveLinkRequest(new URI(link))).toString())
                    .header("tgChatId", userId.toString())
                    .retrieve()
                    .bodyToMono(LinkResponse.class)
                    .block();
        } catch (URISyntaxException | WebClientResponseException e) {
            return "Неккоректная ссылка";
        }
        return "Ссылка больше не отслеживается";
    }
}
