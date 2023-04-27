package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.chat.Link;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Transactional
    public Link addLink(Link link) {
        Optional<Link> optionalLink = this.findLinkByUrl(link.getUrl());
        if (optionalLink.isPresent()) return optionalLink.get();

        String query = "INSERT INTO link(url) VALUES(:url) returning *";
        return jdbcTemplate.queryForObject(
                query,
                Map.of("url", link.getUrl()),
                rowMapper);
    }

    public Iterable<Link> findAllLinks() {
        String query = "select * from link;";
        return jdbcTemplate.query(query, rowMapper);
    }

    public Iterable<Link> findAllLinksById(Long tgChatId) {
        String query = "select link.id, url, last_updated, last_checked, " +
                "last_checked_when_was_updated, last_commit_date, last_answer_date " +
                "from link, chat where tgchatid = :tgchatid and trackedlink = link.id";
        return jdbcTemplate.query(query, Map.of("tgchatid", tgChatId), rowMapper);
    }

    public Optional<Link> findLinkById(Long id) {
        String query = "SELECT * FROM link WHERE id=:id;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("id", id), rowMapper)
                )
        );
    }

    public Optional<Link> findLinkByUrl(String url) {
        String query = "SELECT * FROM link WHERE url=:url;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("url", url), rowMapper)
                )
        );
    }

    @Transactional
    public Optional<Link> removeLinkById(Long id) {
        String query = "delete FROM link WHERE id=:id returning *;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("id", id), rowMapper)
                )
        );
    }

    @Transactional
    public Optional<Link> removeLinkByUrl(String url) {
        String query = "delete FROM link WHERE url=:url returning *;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("url", url), rowMapper)
                )
        );
    }

    public void updateLastCheckDate(String url, OffsetDateTime lastCheckDate) {
        String query = "UPDATE link SET last_checked=:lastcheckdate WHERE url=:url; ";
        jdbcTemplate.update(query, Map.of("lastcheckdate", lastCheckDate, "url", url));
    }

    public void updateLastActivityDate(String url, OffsetDateTime lastActivityDate, OffsetDateTime lastCheckDate) {
        String query = "UPDATE link SET last_updated=:lastactivitydate, last_checked_when_was_updated=:lastcheckdate, " +
                "last_checked = :lastcheckdate WHERE url=:url; ";
        jdbcTemplate.update(query,
                Map.of("lastactivitydate", lastActivityDate, "lastcheckdate", lastCheckDate, "url", url));
    }

    public void updateLastCommitDate(String url, OffsetDateTime lastCommitDate) {
        String query = "UPDATE link SET last_commit_date=:lastcommitdate " +
                "WHERE url=:url; ";
        jdbcTemplate.update(query,
                Map.of("lastcommitdate", lastCommitDate, "url", url));
    }

    public void updateLastAnswerDate(String url, OffsetDateTime lastAnswerDate) {
        String query = "UPDATE link SET last_answer_date=:lastanswerdate " +
                "WHERE url=:url; ";
        jdbcTemplate.update(query,
                Map.of("lastanswerdate", lastAnswerDate, "url", url));
    }
}
