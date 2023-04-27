package ru.tinkoff.edu.java.scrapper.mapper;

import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListLinkResponseLinkMapper {
    public static ListLinksResponse linksToListLinksResponse(Collection<Link> links) {
        List<LinkResponse> linkArrayList = new ArrayList<>();
        for (Link link : links) {
            linkArrayList.add(linkToLinkResponse(link));
        }
        return new ListLinksResponse(linkArrayList, linkArrayList.size());
    }

    public static LinkResponse linkToLinkResponse(Link link) {
        try {
            return new LinkResponse(link.getId(), new URI(link.getUrl()));
        } catch (URISyntaxException ignored) {
            throw new IncorrectDataException("Неверный формат ссылки");
        }
    }
}
