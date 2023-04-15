package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final ClientService clientService;

    @Override
    public int update() {
        AtomicInteger counter = new AtomicInteger();
        LinkParser linkParser = new LinkParser();
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        chatList.forEach(chat -> chat.getTrackedLinksId().forEach(link -> {
            URI linkUrl;
            try {
                linkUrl = new URI(link.getUrl());
                ParseResult parseResult = linkParser.parse(linkUrl);
                switch (parseResult) {
                    case GitHubParseResult gitHubParseResult -> {
                        clientService.sendUpdateToBot(
                                link.getId(),
                                linkUrl,
                                gitHubParseResult.toString(),
                                List.of(chat.getTgChatId()));
                        counter.getAndIncrement();
                    }
                    case StackOverflowParseResult stackOverflowParseResult -> {
                        clientService.sendUpdateToBot(
                                link.getId(),
                                linkUrl,
                                stackOverflowParseResult.toString(),
                                List.of(chat.getTgChatId()));
                        counter.getAndIncrement();
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + parseResult);
                }
            } catch (URISyntaxException ignored) {
            }
        }));
        return 0;
    }
}
