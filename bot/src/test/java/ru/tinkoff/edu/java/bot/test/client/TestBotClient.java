package ru.tinkoff.edu.java.bot.test.client;

import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public record TestBotClient() {
    public ListLinksResponse showTrackedLinks(Long id) {
        List<LinkResponse> links = new ArrayList<>();
        try {
            links.add(new LinkResponse(1L, new URI("github.com/miqqra/OOP")));
            links.add(new LinkResponse(1L, new URI("github.com/miqqra/DepersonalisationService")));
            links.add(new LinkResponse(1L,
                    new URI("stackoverflow.com/questions/49733733")));
        } catch (URISyntaxException ignored) {
        }
        return new ListLinksResponse(links, links.size());
    }
}
