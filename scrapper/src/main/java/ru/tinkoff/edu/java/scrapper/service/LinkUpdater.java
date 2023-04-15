package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;

@Service
@RequiredArgsConstructor
public class LinkUpdater {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    public int update() {
        return 0;
    }

    public GetGitHubInfoResponse getGitHubInfo(String userName, String repositoryName) {
        String path = "/%s/%s".formatted(userName, repositoryName);
        return gitHubClient
                .webClient()
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(GetGitHubInfoResponse.class)
                .block();
    }

    public GetStackOverflowInfoResponse getStackOverflowInfo(Long questionId) {
        return stackOverflowClient
                .webClient()
                .get()
                .uri(uri -> uri.path(String.format("/%d", questionId))
                        .queryParam("site", "stackoverflow")
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
                    httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                })
                .retrieve()
                .bodyToMono(GetStackOverflowInfoResponse.class)
                .block();
    }
}
