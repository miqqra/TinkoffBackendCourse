package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.mapper.ListLinkResponseLinkMapper;
import ru.tinkoff.edu.java.scrapper.repository.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final JdbcLinkDao jdbcLinkDao;
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final ListLinkResponseLinkMapper listLinkResponseLinkMapper;

    public LinkResponse add(long tgChatId, AddLinkRequest addLinkRequest) {
        if (jdbcTgChatRepository.removeChatByTgChatId(tgChatId).isPresent()) {
            return listLinkResponseLinkMapper.LinkToLinkResponse(
                    jdbcTgChatRepository.addLinkToChat(tgChatId, addLinkRequest.getLink().toString())
            );
        } else {
            jdbcTgChatRepository.addChat(tgChatId);
            return listLinkResponseLinkMapper.LinkToLinkResponse(
                    jdbcTgChatRepository.addLinkToChat(tgChatId, addLinkRequest.getLink().toString())
            );
        }
    }

    public LinkResponse remove(long tgChatId, RemoveLinkRequest removeLinkRequest) {
        String url = removeLinkRequest.getLink().toString();
        if (jdbcLinkDao.findLinkByUrl(url).isEmpty()) {
            throw new DataNotFoundException("Ссылка не найдена");
        } else if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isEmpty()) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
        Link link = jdbcTgChatRepository
                .removeChatByUrl(tgChatId, url)
                .orElseThrow(() -> {
                    throw new IncorrectDataException("Некорректные параметры запроса");
                });
        return listLinkResponseLinkMapper.LinkToLinkResponse(link);
    }

    public ListLinksResponse listAll(long tgChatId) {
        Iterable<Link> linkIterable = jdbcLinkDao.findAllLinksById(tgChatId);
        return (new ListLinkResponseLinkMapper().linksToListLinksResponse(linkIterable));
    }
}
