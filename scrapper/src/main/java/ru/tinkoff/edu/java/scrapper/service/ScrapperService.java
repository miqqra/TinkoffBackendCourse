package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.ScrapperRepository;

@Service
public class ScrapperService {
    private final ScrapperRepository scrapperRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Autowired
    public ScrapperService(ScrapperRepository scrapperRepository, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        this.scrapperRepository = scrapperRepository;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    public void registerChat(Long id) {
        if (id == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
    }

    public void deleteChat(Long id) {
        if (id == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (id == 0) {
            throw new DataNotFoundException("Чат не существует");
        }
    }


    public ListLinksResponse getAllTrackedLinks(Long tgChatId) {
        if (tgChatId == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else {
            return null;
        }
    }

    public LinkResponse addTrackedLink(AddLinkRequest addLinkRequest, Long tgChatId) {
        if (tgChatId == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else {
            return null;
        }
    }

    public LinkResponse deleteTrackedLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        if (tgChatId == -1) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (tgChatId == 0) {
            throw new DataNotFoundException("Ссылка не найдена");
        } else {
            return null;
        }
    }

    public GetGitHubInfoResponse getGitHubInfo(String userName, String repositoryName) {
        String path = "%s/%s/commits".formatted(userName, repositoryName);
        GetGitHubInfoResponse[] response = gitHubClient
                .webClient()
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(GetGitHubInfoResponse[].class)
                .block();
        return null;
    }

    public GetStackOverflowInfoResponse GetStackOverflowInfo(long questionId) {
        String path = "%l?site=stackoverflow".formatted(questionId);
        GetStackOverflowInfoResponse[] response = gitHubClient
                .webClient()
                .get()
                .uri(path)
                .retrieve()
                .bodyToMono(GetStackOverflowInfoResponse[].class)
                .block();
        return null;
    }

    public void checkForUpdate() {

    }
}
