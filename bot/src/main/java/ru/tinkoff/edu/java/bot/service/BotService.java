package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;
import ru.tinkoff.edu.java.bot.repository.BotRepository;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessorImpl;

@Service
@RequiredArgsConstructor
public class BotService {
    private final BotRepository botRepository;
    private final WebClient webClient;

    public void sendUpdate(LinkUpdate sendUpdateRequest) {

        if (sendUpdateRequest.getId() == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
    }

    public String showCommandsList() {
        return UserMessageProcessorImpl.showAllCommands();
    }

    public String showTrackedLinks() {
        return null;
    }

    public void registrateUser(Integer id) {
        String path = "tg-chat/%d".formatted(id);
        webClient
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public String startTrackingLink(String newLink) {
        String path = "/links";
        //todo
        LinkResponse linkResponse = webClient
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
        return "Ссылка добавлена";
    }

    public String stopTrackingLink() {
        String path = "/links";
        LinkResponse linkResponse = webClient
                .delete()
                .uri(path)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
        return "Ссылка больше не отслеживается";
    }
}
