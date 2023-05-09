package ru.tinkoff.edu.java.scrapper.service.impl.jpa;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkDao jpaLinkDao;
    private final JpaTgChatRepository jpaTgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Link newLink = new Link(url.toString());
        Chat chat = jpaTgChatRepository.findChatByTgChatId(tgChatId);
        if (chat == null) {
            chat = new Chat(tgChatId, List.of(newLink));
        } else {
            chat.addTrackedLink(newLink);
        }
        jpaTgChatRepository.save(chat);
        return newLink;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Chat chat = jpaTgChatRepository.findChatByTgChatId(tgChatId);
        Link link = jpaLinkDao.findLinkByUrl(url.toString());
        if (link == null) {
            throw new DataNotFoundException("Ссылка не найдена");
        } else if (chat == null) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (chat.deleteTrackingLink(link)) {
            return link;
        } else {
            throw new IncorrectDataException("Такой ссылки нет в пуле отслеживаемых ссылок");
        }
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jpaLinkDao.findAll();
    }
}