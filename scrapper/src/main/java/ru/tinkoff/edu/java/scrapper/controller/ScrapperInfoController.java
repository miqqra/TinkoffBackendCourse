package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.service.ClientService;

@RestController
@RequiredArgsConstructor
public class ScrapperInfoController {
    private final ClientService clientService;

    @GetMapping(value = "/stackoverflow/api")
    public GetStackOverflowInfoResponse getStackOverflowInfo(
            @RequestParam Long id) {
        return clientService
                .getStackOverflowInfo(id);
    }

    @GetMapping("/github/api")
    public GetGitHubInfoResponse getGitHubInfo(
            @RequestParam String username, @RequestParam String repo) {
        return clientService.getGitHubInfo(username, repo);
    }
}
