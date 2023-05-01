package ru.tinkoff.edu.java.scrapper.service.impl.sync;

import org.springframework.web.reactive.function.BodyInserters;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.ClientService;

import java.net.URI;
import java.util.List;

public class BotClientService extends ClientService {
    private final BotClient botClient;

    public BotClientService(GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, BotClient botClient) {
        super(gitHubClient, stackOverflowClient);
        this.botClient = botClient;
    }

    @Override
    public void sendUpdateToBot(Long id, URI url, String description, List<Long> tgChatIds) {
        String path = "/updates";
        botClient
                .webClient()
                .post()
                .uri(path)
                .body(BodyInserters.fromValue(new LinkUpdateRequest(id, url, description, tgChatIds)));
    }
}
