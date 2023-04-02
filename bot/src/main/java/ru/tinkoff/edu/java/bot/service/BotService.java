package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessor;

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

    public String showTrackedLinks(Long id) {
        String path = "/links";
        ListLinksResponse listLinksResponse = botClient
                .webClient()
                .get()
                .uri(path)
                .header("tgChatId", id.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
        return listLinksResponse == null ? "" : listLinksResponse.getLinks().toString();
    }

    public void registrateUser(Integer id) {
        String path = "tg-chat/%d".formatted(id);
        botClient
                .webClient()
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public String startTrackingLink(String newLink) {
        String path = "/links";
        LinkResponse linkResponse = botClient
                .webClient()
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
        return "Ссылка добавлена";
    }

    public String stopTrackingLink(String link) {
        String path = "/links";
        LinkResponse linkResponse = botClient
                .webClient()
                .delete()
                .uri(path)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
        return "Ссылка больше не отслеживается";
    }
}
