package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class ClientConfiguration {

    @Value("${client.url.github}")
    private static String GITHUB_URL = "https://api.github.com/repos";

    @Value(value = "${client.url.stackoverflow}")
    private static String STACKOVERFLOW_URL = "https://api.stackexchange.com/2.3/questions";

    @Bean
    public GitHubClient createGitHubClient() {
        return new GitHubClient(createWebClient(GITHUB_URL));
    }

    @Bean
    public StackOverflowClient createStackOverflowClient() {
        return new StackOverflowClient(createWebClient(STACKOVERFLOW_URL));
    }

    private WebClient createWebClient(String baseURL) {
        return WebClient.builder()
                .baseUrl(baseURL)
                .build();
    }
}
