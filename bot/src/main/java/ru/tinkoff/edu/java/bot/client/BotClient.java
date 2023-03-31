package ru.tinkoff.edu.java.bot.client;

import org.springframework.web.reactive.function.client.WebClient;

public record BotClient(WebClient webClient) {
}
