package ru.tinkoff.edu.java.scrapper.service.impl.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.ClientService;

import java.net.URI;
import java.util.List;

public class ScrapperQueueProducer extends ClientService {
    Logger logger = LoggerFactory.getLogger(ScrapperQueueProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public ScrapperQueueProducer(GitHubClient gitHubClient,
                                 StackOverflowClient stackOverflowClient,
                                 RabbitTemplate rabbitTemplate,
                                 Queue queue) {
        super(gitHubClient, stackOverflowClient);
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @Override
    public void sendUpdateToBot(Long id, URI url, String description, List<Long> tgChatIds) {
        rabbitTemplate.convertAndSend(
                queue.getName(),
                new LinkUpdateRequest(id, url, description, tgChatIds));
    }
}
