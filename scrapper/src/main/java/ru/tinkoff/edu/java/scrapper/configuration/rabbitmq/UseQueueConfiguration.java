package ru.tinkoff.edu.java.scrapper.configuration.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.service.impl.async.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class UseQueueConfiguration {
    @Bean
    public ScrapperQueueProducer scrapperQueueProducer(
            GitHubClient gitHubClient,
            StackOverflowClient stackOverflowClient,
            RabbitTemplate rabbitTemplate,
            Queue queue
    ) {
        return new ScrapperQueueProducer(gitHubClient, stackOverflowClient, rabbitTemplate, queue);
    }
}
