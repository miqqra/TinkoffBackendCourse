package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.db.access.AccessType;
import ru.tinkoff.edu.java.scrapper.configuration.rabbitmq.RabbitMQNames;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull Scheduler scheduler,
                                @NotNull Boolean useQueue,
                                @NotNull AccessType databaseAccessType,
                                @NotNull RabbitMQNames rabbitMQNames) {
    @Bean
    public long schedulerIntervalMs(ApplicationConfig config) {
        return config.scheduler().interval().toMillis();
    }

    @Bean
    public long schedulerCheckMs(ApplicationConfig config) {
        return config.scheduler().check().toMillis();
    }

    @Bean
    public AccessType accessType(ApplicationConfig config) {
        return config.databaseAccessType();
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
    public Boolean useQueue(ApplicationConfig config) {
        return config.useQueue();
    }
}
