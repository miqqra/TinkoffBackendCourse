package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.configuration.rabbitmq.RabbitMQNames;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String token,
                                @NotNull Boolean useQueue,
                                @NotNull RabbitMQNames rabbitMQNames) {
    @Bean
    public String getBotToken(ApplicationConfig config) {
        return config.token();
    }

    @Bean
    public String queueName(ApplicationConfig config) {
        return config.rabbitMQNames().queueName();
    }

    @Bean
    public String exchangeName(ApplicationConfig config) {
        return config.rabbitMQNames().exchangeName();
    }

    @Bean
    Boolean useQueue(ApplicationConfig config) {
        return config.useQueue();
    }
}
