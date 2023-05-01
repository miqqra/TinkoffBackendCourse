package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String token, @NotNull ScrapperQueue scrapperQueue) {
    @Bean
    public String getBotToken(ApplicationConfig config){
        return config.token();
    }

    @Bean
    public String queueName(ApplicationConfig config){
        return config.scrapperQueue().name();
    }
}
