package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;

public record BotClient(WebClient webClient) {
}
