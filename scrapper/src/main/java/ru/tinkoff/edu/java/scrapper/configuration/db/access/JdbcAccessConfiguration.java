package ru.tinkoff.edu.java.scrapper.configuration.db.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.ClientService;
import ru.tinkoff.edu.java.scrapper.service.impl.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.impl.jdbc.JdbcLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.impl.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkService jdbcLinkService(JdbcLinkDao jdbcLinkDao, JdbcTgChatRepository jdbcTgChatRepository) {
        return new JdbcLinkService(jdbcLinkDao, jdbcTgChatRepository);
    }

    @Bean
    public JdbcLinkUpdater jdbcLinkUpdater(JdbcTgChatRepository jdbcTgChatRepository,
                                           JdbcLinkDao jdbcLinkDao, ClientService clientService) {
        return new JdbcLinkUpdater(jdbcTgChatRepository, jdbcLinkDao, clientService);
    }

    @Bean
    public JdbcTgChatService jdbcTgChatService(JdbcTgChatRepository jdbcTgChatRepository) {
        return new JdbcTgChatService(jdbcTgChatRepository);
    }

}