package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubCommitResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackoverflowAnswerResponse;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

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

    public GetGitHubCommitResponse getGitHubCommit(String userName, String repositoryName){
        String path = "/%s/%s/commits".formatted(userName, repositoryName);
        return gitHubClient
                .webClient()
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(GetGitHubCommitResponse.class)
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

    public GetStackoverflowAnswerResponse getStackoverflowAnswer(Long questionId){
        return stackOverflowClient
                .webClient()
                .get()
                .uri(uri -> uri.path(String.format("/%d/answers", questionId))
                        .queryParam("site", "stackoverflow")
                        .queryParam("order", "desc")
                        .build())
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
                    httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                })
                .retrieve()
                .bodyToMono(GetStackoverflowAnswerResponse.class)
                .block();
    }

    public void sendUpdateToBot(Long id, URI url, String description, List<Long> tgChatIds) {
        String path = "/updates";
        botClient
                .webClient()
                .post()
                .uri(path)
                .body(BodyInserters.fromValue(new LinkUpdateRequest(id, url, description, tgChatIds)));
    }
}
