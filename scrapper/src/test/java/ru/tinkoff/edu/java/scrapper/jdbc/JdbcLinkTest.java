package ru.tinkoff.edu.java.scrapper.jdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.chat.Link;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkDao;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkDao linkRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Link link1 = new Link("github.com");
        Link link2 = new Link("stackoverflow.com");

        linkRepository.addLink(link1);
        linkRepository.addLink(link2);

        Iterable<Link> links = linkRepository.findAllLinks();

        assertThat(links, is(notNullValue()));

        List<Link> list = new ArrayList<>();
        for (Link link : links) {
            list.add(link);
        }

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getUrl(), equalTo("github.com"));
        assertThat(list.get(1).getUrl(), equalTo("stackoverflow.com"));
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Link link1 = new Link(1L, "github.com");
        Link link2 = new Link(2L, "stackoverflow.com");
        Link link3 = new Link(3L, "notstackoverflow.com");
        Link link4 = new Link(4L, "reallynotstackoverflow.com");

        Link addedLink1 = linkRepository.addLink(link1);
        Link addedLink2 = linkRepository.addLink(link2);
        linkRepository.addLink(link3);
        linkRepository.addLink(link4);

        Link removedLink1 = linkRepository.removeLinkById(addedLink1.getId()).orElse(null);
        Link removedLink2 = linkRepository.removeLinkById(addedLink2.getId()).orElse(null);

        Iterable<Link> links = linkRepository.findAllLinks();

        assertThat(removedLink1, is(notNullValue()));
        assertThat(removedLink2, is(notNullValue()));

        assertThat(removedLink1.getUrl(), equalTo("github.com"));
        assertThat(removedLink2.getUrl(), equalTo("stackoverflow.com"));

        assertThat(links, is(notNullValue()));

        List<Link> list = new ArrayList<>();
        for (Link link : links) {
            list.add(link);
        }

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getUrl(), equalTo("notstackoverflow.com"));
        assertThat(list.get(1).getUrl(), equalTo("reallynotstackoverflow.com"));
    }
}
