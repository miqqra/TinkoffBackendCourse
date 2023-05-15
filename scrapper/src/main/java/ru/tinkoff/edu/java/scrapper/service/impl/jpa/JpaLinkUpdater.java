package ru.tinkoff.edu.java.scrapper.service.impl.jpa;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubCommitResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackoverflowAnswerResponse;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.impl.sync.BotClientService;

@RequiredArgsConstructor
public class JpaLinkUpdater extends LinkUpdater {
    private final JpaTgChatRepository jpaTgChatRepository;
    private final JpaLinkDao jpaLinkDao;
    private final BotClientService clientService;
    @Value("#{@schedulerCheckMs}")
    private Long checkTime;
    private LinkParser linkParser = new LinkParser();

    public int updateAll() {
        List<Chat> chatList = jpaTgChatRepository.findAll();
        return iterateOverAllLinks(chatList, linkParser, clientService);
    }

    public int updateUncheckedLinks() {
        List<Chat> chatList = jpaTgChatRepository.findAll();
        return iterateOverUncheckedLinks(chatList, checkTime, linkParser, clientService);
    }

    protected boolean checkForUpdateTimeNewness(OffsetDateTime lastUpdate, OffsetDateTime updateTime, Link link) {
        if (link.getLastUpdated() == null || lastUpdate != link.getLastUpdated()) {
            link.setLastUpdated(lastUpdate);
            link.setLastChecked(updateTime);
            jpaLinkDao.save(link);
            return true;
        } else {
            link.setLastChecked(updateTime);
            jpaLinkDao.save(link);
            return false;
        }
    }

    protected void checkForNewCommit(GetGitHubCommitResponse.GitHubCommitAuthor lastCommitInfo,
                                     Link link, Chat chat, URI linkUrl) {
        if (link.getLastCommitDate() == null) {
            link.setLastCommitDate(lastCommitInfo.date());
            jpaLinkDao.save(link);
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Last commit at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else if (link.getLastCommitDate().isBefore(lastCommitInfo.date())) {
            link.setLastCommitDate(lastCommitInfo.date());
            jpaLinkDao.save(link);
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates with %s: %s made new commit at %s"
                            .formatted(link.getUrl(), lastCommitInfo.name(), lastCommitInfo.date().toString()));
        } else {
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Found new updates in %s"
                            .formatted(link.getUrl()));
        }
    }

    protected void checkForNewAnswer(GetStackoverflowAnswerResponse.StackOverflowAnswers lastAnswerInfo,
                                     Link link, Chat chat, URI linkUrl) {
        if (link.getLastAnswerDate() == null) {
            link.setLastAnswerDate(lastAnswerInfo.last_activity_date());
            jpaLinkDao.save(link);
            sendUpdatesToBot(clientService, chat, linkUrl,
                    "Last answer at %s was made by %s at %s"
                            .formatted(link.getUrl(), lastAnswerInfo.owner().display_name(),
                                    lastAnswerInfo.last_activity_date().toString()));
        } else if (link.getLastAnswerDate().isBefore(lastAnswerInfo.last_activity_date())) {
            link.setLastAnswerDate(lastAnswerInfo.last_activity_date());
            jpaLinkDao.save(link);
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