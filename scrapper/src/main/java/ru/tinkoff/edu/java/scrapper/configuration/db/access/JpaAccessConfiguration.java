package ru.tinkoff.edu.java.scrapper.configuration.db.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.impl.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.impl.jpa.JpaLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.impl.jpa.JpaTgChatService;
import ru.tinkoff.edu.java.scrapper.service.impl.sync.BotClientService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public JpaLinkService jpaLinkService(JpaLinkDao jpaLinkDao, JpaTgChatRepository jpaTgChatRepository) {
        return new JpaLinkService(jpaLinkDao, jpaTgChatRepository);
    }

    @Bean
    public JpaLinkUpdater jpaLinkUpdater(JpaTgChatRepository jpaTgChatRepository,
                                         JpaLinkDao jpaLinkDao, BotClientService clientService) {
        return new JpaLinkUpdater(jpaTgChatRepository, jpaLinkDao, clientService);
    }

    @Bean
    public JpaTgChatService jpaTgChatService(JpaTgChatRepository jpaTgChatRepository) {
        return new JpaTgChatService(jpaTgChatRepository);
    }
}
