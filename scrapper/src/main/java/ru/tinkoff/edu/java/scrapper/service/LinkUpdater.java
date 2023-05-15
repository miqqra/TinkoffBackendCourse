package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubCommitResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackoverflowAnswerResponse;

public abstract class LinkUpdater {

    public abstract int updateAll();

    public abstract int updateUncheckedLinks();

    public int iterateOverAllLinks(List<Chat> chatList, LinkParser linkParser, ClientService clientService) {
        AtomicInteger counter = new AtomicInteger();
        OffsetDateTime updateTime = OffsetDateTime.now(ZoneOffset.of("+07:00"));

        chatList.forEach(chat -> chat.getTrackedLinksId().forEach(link -> {
            URI linkUrl;
            try {
                linkUrl = new URI(link.getUrl());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            counter.addAndGet(checkLinkForUpdate(linkParser, clientService, link, chat, updateTime, linkUrl));
        }));
        return counter.get();
    }

    public int iterateOverUncheckedLinks(List<Chat> chatList, long checkTime,
                                         LinkParser linkParser, ClientService clientService) {
        AtomicInteger counter = new AtomicInteger();
        OffsetDateTime updateTime = ZonedDateTime.now(ZoneOffset.of("+07:00"))
                .withZoneSameInstant(ZoneId.of("UTC-8")).toOffsetDateTime();

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
                counter.addAndGet(checkLinkForUpdate(linkParser, clientService, link, chat, updateTime, linkUrl));
            }
        }));
        return counter.get();
    }

    public void sendUpdatesToBot(ClientService clientService, Chat chat, URI url, String description) {
        clientService.sendUpdateToBot(
                chat.getTgChatId(),
                url,
                description,
                List.of(chat.getTgChatId()));
    }

    public int checkLinkForUpdate(LinkParser linkParser, ClientService clientService,
                                  Link link, Chat chat, OffsetDateTime updateTime, URI linkUrl) {
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
                        .getGitHubInfo(gitHubParseResult.getUser(), gitHubParseResult.getRepository()).updated_at();
                List<GetGitHubCommitResponse> commits = clientService
                        .getGitHubCommit(gitHubParseResult.getUser(), gitHubParseResult.getRepository());

                if (commits.isEmpty()) return 0;

                GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo = commits
                        .get(0).commit().author();

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

                GetStackoverflowAnswerResponse answers = clientService
                        .getStackoverflowAnswer(stackOverflowParseResult.getQuestionId());

                if (answers == null || answers.answers().isEmpty()) return 0;

                GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo = answers.answers().get(0);

                if (linkWasCheckedEarlier || checkForUpdateTimeNewness(lastUpdate, updateTime, link)) {
                    counter++;
                    checkForNewAnswer(lastAnswerInfo, link, chat, linkUrl);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + parseResult);
        }
        return counter;
    }

    protected abstract boolean checkForUpdateTimeNewness(OffsetDateTime lastUpdate, OffsetDateTime updateTime, Link link);

    protected abstract void checkForNewCommit(GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo,
                                              Link link, Chat chat, URI linkUrl);

    protected abstract void checkForNewAnswer(GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo,
                                              Link link, Chat chat, URI linkUrl);
}