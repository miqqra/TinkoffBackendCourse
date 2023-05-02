package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class ClientConfiguration {

    @Value("${client.url.github:https://api.github.com/repos}")
    private String GITHUB_URL;

    @Value(value = "${client.url.stackoverflow:https://api.stackexchange.com/2.3/questions}")
    private String STACKOVERFLOW_URL;

    @Value("${client.url.bot:http://localhost:8081}")
    private String BOT_URL;

    @Bean
    public GitHubClient createGitHubClient() {
        return new GitHubClient(createWebClient(GITHUB_URL));
    }

    @Bean
    public StackOverflowClient createStackOverflowClient() {
        return new StackOverflowClient(createWebClient(STACKOVERFLOW_URL));
    }

    @Bean
    public BotClient createBotClient(){
        return new BotClient(createWebClient(BOT_URL));
    }

    private WebClient createWebClient(String baseURL) {
        return WebClient.builder()
                .baseUrl(baseURL)
                .build();
    }
}
