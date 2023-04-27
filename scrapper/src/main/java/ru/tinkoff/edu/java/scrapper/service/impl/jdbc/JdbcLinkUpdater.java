package ru.tinkoff.edu.java.scrapper.service.impl.jdbc;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubCommitResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackoverflowAnswerResponse;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;


@RequiredArgsConstructor
public class JdbcLinkUpdater extends LinkUpdater {
    Logger logger = LoggerFactory.getLogger(JdbcLinkUpdater.class);
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final JdbcLinkDao jdbcLinkDao;
    private final ClientService clientService;
    @Value("#{@schedulerCheckMs}")
    private Long checkTime;
    private LinkParser linkParser = new LinkParser();

    public int updateAll() {
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        return iterateOverAllLinks(chatList, linkParser, clientService);
    }

    public int updateUncheckedLinks() {
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        return iterateOverUncheckedLinks(chatList, checkTime, linkParser, clientService);
    }

    protected boolean checkForUpdateTimeNewness(OffsetDateTime lastUpdate, OffsetDateTime updateTime, Link link) {
        if (link.getLastUpdated() == null || !lastUpdate.isEqual(link.getLastUpdated())) {
            jdbcLinkDao.updateLastActivityDate(link.getUrl(), lastUpdate, updateTime);
            return true;
        } else {
            jdbcLinkDao.updateLastCheckDate(link.getUrl(), updateTime);
            return false;
        }
    }

    protected void checkForNewCommit(GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo,
                                     Link link, Chat chat, URI linkUrl) {
        if (link.getLastCommitDate() == null) {
            logger.info("Last commit at %s was made by %s at %s"
                    .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
            jdbcLinkDao.updateLastCommitDate(link.getUrl(), lastCommitInfo.date());
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Last commit at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else if (link.getLastCommitDate().isAfter(lastCommitInfo.date())) {
            logger.info("Found new updates with %s: %s made new commit at %s"
                    .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
            jdbcLinkDao.updateLastCommitDate(link.getUrl(), lastCommitInfo.date());
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates with %s: %s made new commit at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else {
            logger.info("Found new updates in %s"
                    .formatted(link.getUrl()));
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates in %s"
                            .formatted(link.getUrl()));
        }
    }

    protected void checkForNewAnswer(GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo,
                                     Link link, Chat chat, URI linkUrl) {
        if (link.getLastAnswerDate() == null) {
            jdbcLinkDao.updateLastAnswerDate(link.getUrl(), lastAnswerInfo.last_activity_date());
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Last answer at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastAnswerInfo.owner().display_name(),
                                    lastAnswerInfo.last_activity_date().toString()));
        } else if (link.getLastAnswerDate().isAfter(lastAnswerInfo.last_activity_date())) {
            jdbcLinkDao.updateLastAnswerDate(link.getUrl(), lastAnswerInfo.last_activity_date());
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates with %s: %s left new answer at %s"
                            .formatted(link.getUrl(), lastAnswerInfo.owner().display_name(),
                                    lastAnswerInfo.last_activity_date().toString()));
        } else {
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates in %s"
                            .formatted(link.getUrl()));
        }
    }
}