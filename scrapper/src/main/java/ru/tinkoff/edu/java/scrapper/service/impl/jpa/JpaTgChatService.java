package ru.tinkoff.edu.java.scrapper.service.impl.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.exception.DataNotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.ExistingDataException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectDataException;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaTgChatRepository jpaTgChatRepository;

    @Transactional
    @Override
    public void register(long tgChatId) {
        if (tgChatId <= 0) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (jpaTgChatRepository.findChatByTgChatId(tgChatId) != null) {
            throw new ExistingDataException("Чат с таким id уже существует");
        } else {
            Chat chat = new Chat();
            chat.setTgChatId(tgChatId);
            jpaTgChatRepository.save(chat);
        }
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = jpaTgChatRepository.findChatByTgChatId(tgChatId);
        System.out.println("a");
        System.out.println(chat);
        if (tgChatId <= 0) {
            throw new IncorrectDataException("Некорректные параметры запроса");
        } else if (chat == null) {
            throw new DataNotFoundException("Чат не существует");
        } else {
            jpaTgChatRepository.delete(chat);
        }
    }
}