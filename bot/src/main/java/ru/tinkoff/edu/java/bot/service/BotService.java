package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.exception.IncorrectDataException;
import ru.tinkoff.edu.java.bot.repository.BotRepository;

@Service
@RequiredArgsConstructor
public class BotService {
    private final BotRepository botRepository;

    public void sendUpdate(LinkUpdate sendUpdateRequest) {
        if (sendUpdateRequest.getId() == -1){
            throw new IncorrectDataException("Некорректные параметры запроса");
        }
    }
}
