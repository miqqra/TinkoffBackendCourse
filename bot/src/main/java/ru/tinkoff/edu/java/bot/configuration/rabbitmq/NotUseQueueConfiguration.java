package ru.tinkoff.edu.java.bot.configuration.rabbitmq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.service.RestBotService;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class NotUseQueueConfiguration {
    @Bean
    public RestBotService restBotService(Bot bot, BotClient botClient){
        return new RestBotService(bot, botClient);
    }
}
