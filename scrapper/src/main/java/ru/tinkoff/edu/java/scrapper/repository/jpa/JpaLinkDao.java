package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.chat.Link;

public interface JpaLinkDao extends JpaRepository<Link, Long> {
    Link findLinkByUrl(String url);
}