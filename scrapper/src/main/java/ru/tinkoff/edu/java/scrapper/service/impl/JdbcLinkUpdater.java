package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final JdbcLinkDao jdbcLinkDao;
    private final ClientService clientService;
    @Value("#{@schedulerCheckMs}")
    private Long checkTime;
    private LinkParser linkParser = new LinkParser();

    @Override
    public int updateAll() {
        AtomicInteger counter = new AtomicInteger();
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        OffsetDateTime updateTime = OffsetDateTime.now();

        chatList.forEach(chat -> chat.getTrackedLinksId().forEach(link -> {
            URI linkUrl;
            try {
                linkUrl = new URI(link.getUrl());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            counter.addAndGet(checkLinkForUpdate(link, chat, updateTime, linkUrl));
        }));
        return counter.get();
    }

    @Override
    public int updateUncheckedLinks() {
        AtomicInteger counter = new AtomicInteger();
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        OffsetDateTime updateTime = OffsetDateTime.now();

        chatList.forEach(chat -> chat.getTrackedLinksId().forEach(link -> {
            URI linkUrl;
            try {
                linkUrl = new URI(link.getUrl());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            if (link.getLastChecked() == null || !Duration
                    .between(updateTime, link.getLastChecked())
                    .minus(Duration.ofMillis(checkTime))
                    .isNegative()) {
                counter.addAndGet(checkLinkForUpdate(link, chat, updateTime, linkUrl));
            }
        }));
        return counter.get();
    }

    private int checkLinkForUpdate(Link link, Chat chat, OffsetDateTime updateTime, URI linkUrl) {
        int counter = 0;
        //link was checked in this session of updates
        if (link.getLastChecked() != null && Duration.between(updateTime, link.getLastChecked()).isZero()) {
            //link was updated in this session
            if (link.getLastCheckedWhenWasUpdated().equals(link.getLastChecked())) {
                sendUpdatesToBot(chat, linkUrl);
            }
        } else {
            ParseResult parseResult = linkParser.parse(linkUrl);
            switch (parseResult) {
                case GitHubParseResult gitHubParseResult -> {
                    OffsetDateTime lastUpdate = clientService
                            .getGitHubInfo(gitHubParseResult.getUser(), gitHubParseResult.getRepository())
                            .lastUpdate();

                    if (checkForUpdateTimeNewness(lastUpdate, updateTime, link, chat, linkUrl)) {
                        counter++;
                    }
                }
                case StackOverflowParseResult stackOverflowParseResult -> {
                    OffsetDateTime lastUpdate = clientService
                            .getStackOverflowInfo(stackOverflowParseResult.getQuestionId())
                            .items()[0]
                            .last_activity_date();

                    if (checkForUpdateTimeNewness(lastUpdate, updateTime, link, chat, linkUrl)) {
                        counter++;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + parseResult);
            }
        }
        return counter;
    }

    private boolean checkForUpdateTimeNewness(OffsetDateTime lastUpdate, OffsetDateTime updateTime,
                                              Link link, Chat chat, URI linkUrl) {
        if (link.getLastUpdated() == null) {
            jdbcLinkDao.updateLastActivityDate(link.getUrl(), lastUpdate, updateTime);
            sendFirstUpdateToBot(chat, linkUrl);
            return true;
        }
        if (lastUpdate != link.getLastUpdated()) {
            jdbcLinkDao.updateLastActivityDate(link.getUrl(), lastUpdate, updateTime);
            sendUpdatesToBot(chat, linkUrl);
            return true;
        } else {
            jdbcLinkDao.updateLastCheckDate(link.getUrl(), updateTime);
            return false;
        }
    }

    private void sendUpdatesToBot(Chat chat, URI url) {
        clientService.sendUpdateToBot(
                chat.getTgChatId(),
                url,
                "There are some changes with link: " + url,
                List.of(chat.getTgChatId()));
    }

    private void sendFirstUpdateToBot(Chat chat, URI url) {
        clientService.sendUpdateToBot(
                chat.getTgChatId(),
                url,
                "Got last change from link: " + url,
                List.of(chat.getTgChatId())
        );
    }
}
