package ru.tinkoff.edu.java.scrapper.service.impl.jdbc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ExistingDataException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao jdbcLinkDao;
    private final JdbcTgChatRepository jdbcTgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = jdbcTgChatRepository.findChatByTgChatId(tgChatId).orElse(null);
        Link link = jdbcLinkDao.findLinkByUrl(url.toString()).orElse(null);

        if (chat == null && link == null) {
            link = new Link(url.toString());
            jdbcTgChatRepository.addChat(new Chat(tgChatId, List.of(new Link(url.toString()))));
        } else if (chat == null) {
            jdbcTgChatRepository.addChat(new Chat(tgChatId, List.of(link)));
        } else if (link == null) {
            link = jdbcTgChatRepository.addLinkToChat(chat.getTgChatId(), url.toString());
        } else {
            Link finalLink = link;
            if (chat
                    .getTrackedLinksId()
                    .stream()
                    .anyMatch(x -> x.getId().equals(finalLink.getId()))) {
                throw new ExistingDataException("Ссылка уже отслеживается");
            } else {
                jdbcTgChatRepository.addLinkToChat(chat.getTgChatId(), link.getUrl());
            }
        }
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = jdbcLinkDao.findLinkByUrl(url.toString()).orElse(null);
        Chat chat = jdbcTgChatRepository.findChatByTgChatId(tgChatId).orElse(null);
        if (link == null) {
            throw new DataNotFoundException("Ссылка не найдена");
        } else if (chat == null) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (chat
                .getTrackedLinksId()
                .stream()
                .noneMatch(x -> x.getUrl().equals(url.toString()))) {
            throw new ExistingDataException("Ссылка не найдена");
        }
        return jdbcTgChatRepository
                .removeChatByUrl(chat, link).get();
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        Iterable<Link> linkIterable = jdbcLinkDao.findAllLinksById(tgChatId);
        List<Link> links = new ArrayList<>();
        for (Link link : linkIterable) links.add(link);
        return links;
    }
}