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
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubCommitResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackoverflowAnswerResponse;
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
        boolean linkWasCheckedEarlier = false;
        //link was checked in this session of updates
        if (link.getLastChecked() != null && Duration.between(updateTime, link.getLastChecked()).isZero()) {
            //link was updated in this session
            if (link.getLastCheckedWhenWasUpdated().equals(link.getLastChecked())) {
                linkWasCheckedEarlier = true;
            } else {
                return ++counter;
            }
        }
        ParseResult parseResult = linkParser.parse(linkUrl);
        switch (parseResult) {
            case GitHubParseResult gitHubParseResult -> {
                OffsetDateTime lastUpdate = clientService
                        .getGitHubInfo(gitHubParseResult.getUser(), gitHubParseResult.getRepository())
                        .lastUpdate();
                GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo = clientService
                        .getGitHubCommit(gitHubParseResult.getUser(), gitHubParseResult.getRepository())
                        .commits()[0].author();

                if (linkWasCheckedEarlier || checkForUpdateTimeNewness(lastUpdate, updateTime, link)) {
                    counter++;
                    checkForNewCommit(lastCommitInfo, link, chat, linkUrl);
                }
            }
            case StackOverflowParseResult stackOverflowParseResult -> {
                OffsetDateTime lastUpdate = clientService
                        .getStackOverflowInfo(stackOverflowParseResult.getQuestionId())
                        .items()[0]
                        .last_activity_date();
                GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo = clientService
                        .getStackoverflowAnswer(stackOverflowParseResult.getQuestionId())
                        .answers()[0];

                if (linkWasCheckedEarlier || checkForUpdateTimeNewness(lastUpdate, updateTime, link)) {
                    counter++;
                    checkForNewAnswer(lastAnswerInfo, link, chat, linkUrl);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + parseResult);
        }
        return counter;
    }

    private boolean checkForUpdateTimeNewness(OffsetDateTime lastUpdate, OffsetDateTime updateTime, Link link) {
        if (link.getLastUpdated() == null || lastUpdate != link.getLastUpdated()) {
            jdbcLinkDao.updateLastActivityDate(link.getUrl(), lastUpdate, updateTime);
            return true;
        } else {
            jdbcLinkDao.updateLastCheckDate(link.getUrl(), updateTime);
            return false;
        }
    }

    private void sendUpdatesToBot(Chat chat, URI url, String description) {
        clientService.sendUpdateToBot(
                chat.getTgChatId(),
                url,
                description,
                List.of(chat.getTgChatId()));
    }

    private void checkForNewCommit(GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo,
                                   Link link, Chat chat, URI linkUrl) {
        if (link.getLastCommitDate() == null) {
            jdbcLinkDao.updateLastCommitDate(link.getUrl(), lastCommitInfo.date());
            sendUpdatesToBot(chat, linkUrl,
                    "Last commit at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else if (link.getLastCommitDate().isBefore(lastCommitInfo.date())) {
            jdbcLinkDao.updateLastCommitDate(link.getUrl(), lastCommitInfo.date());
            sendUpdatesToBot(chat, linkUrl,
                    "Found new updates with %s: %s made new commit at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else {
            sendUpdatesToBot(chat, linkUrl,
                    "Found new updates in %s"
                            .formatted(link.getUrl()));
        }
    }

    private void checkForNewAnswer(GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo,
                                   Link link, Chat chat, URI linkUrl) {
        if (link.getLastAnswerDate() == null) {
            jdbcLinkDao.updateLastAnswerDate(link.getUrl(), lastAnswerInfo.last_activity_date());
            sendUpdatesToBot(chat, linkUrl,
                    "Last answer at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastAnswerInfo.owner().display_name(),
                                    lastAnswerInfo.last_activity_date().toString()));
        } else if (link.getLastAnswerDate().isBefore(lastAnswerInfo.last_activity_date())) {
            jdbcLinkDao.updateLastAnswerDate(link.getUrl(), lastAnswerInfo.last_activity_date());
            sendUpdatesToBot(chat, linkUrl,
                    "Found new updates with %s: %s left new answer at %s"
                            .formatted(link.getUrl(), lastAnswerInfo.owner().display_name(),
                                    lastAnswerInfo.last_activity_date().toString()));
        } else {
            sendUpdatesToBot(chat, linkUrl,
                    "Found new updates in %s"
                            .formatted(link.getUrl()));
        }
    }
}
