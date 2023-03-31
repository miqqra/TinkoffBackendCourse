package ru.tinkoff.edu.java.bot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class ClientConfiguration {
    @Value("${client.url.scrapper}")
    private static final String SCRAPPER_URL = "http://localhost:8080/";

    @Bean
    public BotClient createBotClient() {
        return new BotClient(createWebClient(SCRAPPER_URL));
    }

    public BotClient createBotClient(String newURL) {
        return new BotClient(createWebClient(newURL));
    }

    private WebClient createWebClient(String baseURL) {
        return WebClient.builder()
                .baseUrl(baseURL)
                .build();
    }
}
