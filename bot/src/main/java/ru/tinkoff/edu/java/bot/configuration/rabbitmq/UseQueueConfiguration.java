package ru.tinkoff.edu.java.bot.configuration.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.service.ScrapperQueueListener;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class UseQueueConfiguration {
    @Bean
    public ScrapperQueueListener scrapperQueueListener(Bot bot) {
        return new ScrapperQueueListener(bot);
    }
}
