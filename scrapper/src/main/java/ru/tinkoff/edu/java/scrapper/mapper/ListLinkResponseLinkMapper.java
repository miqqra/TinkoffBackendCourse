package ru.tinkoff.edu.java.scrapper.mapper;

import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListLinkResponseLinkMapper {
    public static ListLinksResponse linksToListLinksResponse(Collection<Link> links) {
        List<LinkResponse> linkArrayList = new ArrayList<>();
        try {
            for (Link link : links) {
                linkArrayList.add(new LinkResponse(link.getId(), new URI(link.getUrl())));
            }
        } catch (URISyntaxException ignored) {
        }
        return new ListLinksResponse(linkArrayList, linkArrayList.size());
    }

    public static LinkResponse LinkToLinkResponse(Link link) {
        try {
            return new LinkResponse(link.getId(), new URI(link.getUrl()));
        } catch (URISyntaxException ignored) {
        }
        return null;
    }
}
