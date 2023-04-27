package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkDao jdbcLinkDao;
    private final JdbcTgChatRepository jdbcTgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isEmpty()) {
            jdbcTgChatRepository.addChat(tgChatId);
        }
        return jdbcTgChatRepository.addLinkToChat(tgChatId, url.toString());
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        if (jdbcLinkDao.findLinkByUrl(url.toString()).isEmpty()) {
            throw new DataNotFoundException("Ссылка не найдена");
        } else if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isEmpty()) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
        return jdbcTgChatRepository
                .removeChatByUrl(tgChatId, url.toString())
                .orElseThrow(() -> {
                    throw new IncorrectDataException("Некорректные параметры запроса");
                });
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        Iterable<Link> linkIterable = jdbcLinkDao.findAllLinksById(tgChatId);
        List<Link> links = new ArrayList<>();
        for (Link link : linkIterable) links.add(link);
        return links;
    }
}
