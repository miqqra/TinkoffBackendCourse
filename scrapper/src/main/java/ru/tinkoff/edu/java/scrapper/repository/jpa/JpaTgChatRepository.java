package ru.tinkoff.edu.java.scrapper.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.chat.Chat;

public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
    Chat findChatByTgChatId(Long tgChatId);
}