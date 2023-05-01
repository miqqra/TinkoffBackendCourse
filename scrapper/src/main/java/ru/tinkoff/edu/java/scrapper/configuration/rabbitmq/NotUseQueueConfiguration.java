package ru.tinkoff.edu.java.scrapper.configuration.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.service.impl.sync.BotClientService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class NotUseQueueConfiguration {
    @Bean
    public BotClientService botClientService(GitHubClient gitHubClient,
                                             StackOverflowClient stackOverflowClient,
                                             BotClient botClient){
        return new BotClientService(gitHubClient, stackOverflowClient, botClient);
    }
}
