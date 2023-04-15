package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RestController
@RequiredArgsConstructor
public class ScrapperLinkController {
    private final LinkService linkService;

    @GetMapping("/links")
    public ListLinksResponse getAllTrackedLinks(@RequestHeader Long tgChatId) {
        return linkService.listAll(tgChatId);
    }

    @PostMapping("/links")
    public LinkResponse addTrackedLink(
            @RequestBody AddLinkRequest addLinkRequest,
            @RequestHeader Long tgChatId) {
        return linkService.add(tgChatId, addLinkRequest);
    }

    @DeleteMapping("/links")
    public LinkResponse deleteTrackedLink(
            @RequestHeader Long tgChatId,
            @RequestBody RemoveLinkRequest removeLinkRequest) {
        return linkService.remove(tgChatId, removeLinkRequest);
    }

}
