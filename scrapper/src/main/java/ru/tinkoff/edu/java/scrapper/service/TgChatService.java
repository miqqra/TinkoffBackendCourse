package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ExistingDataException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;

@Service
@RequiredArgsConstructor
public class TgChatService {
    private final JdbcTgChatRepository jdbcTgChatRepository;

    public void register(long tgChatId) {
        if (tgChatId <= 0) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isPresent()) {
            throw new ExistingDataException("Чат с таким id уже существует");
        } else {
            jdbcTgChatRepository.addChat(tgChatId);
        }

    }

    public void unregister(long tgChatId) {
        if (tgChatId <= 0) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isEmpty()) {
            throw new DataNotFoundException("Чат не существует");
        } else {
            jdbcTgChatRepository.removeChatByTgChatId(tgChatId);
        }
    }
}
