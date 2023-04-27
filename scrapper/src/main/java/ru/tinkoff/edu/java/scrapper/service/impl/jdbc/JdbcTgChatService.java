package ru.tinkoff.edu.java.scrapper.service.impl.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ExistingDataException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcTgChatRepository jdbcTgChatRepository;

    @Override
    public void register(long tgChatId) {
        if (tgChatId <= 0) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (jdbcTgChatRepository.findChatByTgChatId(tgChatId).isPresent()) {
            throw new ExistingDataException("Чат с таким id уже существует");
        } else {
            jdbcTgChatRepository.addChat(tgChatId);
        }
    }

    @Override
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
