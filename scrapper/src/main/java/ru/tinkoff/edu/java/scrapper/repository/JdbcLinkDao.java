package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Link;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    public Optional<Link> addLink(Link link) {
        String query = "insert into link values(?, ?)";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                query,
                                rowMapper,
                                link.getId(), link.getUrl()
                        )
                )
        );
    }

    public List<Link> findAllLinks() {
        String query = "select * from link";
        return jdbcTemplate.query(query, rowMapper);
    }

    public Optional<Link> findLinkById(Long id) {
        String query = "select * from link where id=?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(query, rowMapper, id)
                )
        );
    }

    public Optional<Link> removeLinkById(Long id) {
        String query = "delete from link where id=?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                query, rowMapper, id
                        )
                )
        );
    }
}
