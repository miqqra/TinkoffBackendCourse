package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Override
    public int update() {
        int counter = 0;
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        chatList.forEach(chat -> {
                    chat.getTrackedLinksId().forEach(link -> {
                        //todo
                        if (link.getUrl().contains("github")) {
                            GetGitHubInfoResponse getGitHubInfoResponse =
                                    getGitHubInfo("", "");
                        } else if (link.getUrl().contains("stackoverflow")) {
                            GetStackOverflowInfoResponse getStackOverflowInfoResponse =
                                    getStackOverflowInfo(-1L);
                        } else {
                        }
                    });
                }
        );
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
