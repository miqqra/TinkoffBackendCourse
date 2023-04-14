package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Link;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

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

    public Optional<Link> removeLinkById(Long id) {
        String query = "delete FROM link WHERE id=:id returning *;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("id", id), rowMapper)
                )
        );
    }

    public Optional<Link> removeLinkByUrl(String url) {
        String query = "delete FROM link WHERE url=:url returning *;";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, Map.of("url", url), rowMapper)
                )
        );
    }
}
