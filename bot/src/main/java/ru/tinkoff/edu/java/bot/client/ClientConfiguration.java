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
    public WebClient createWebClient() {
        return WebClient.builder()
                .baseUrl(SCRAPPER_URL)
                .build();
    }
}
