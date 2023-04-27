package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final Bot bot;

    @PostMapping(value = "/updates")
    public void sendUpdate(@RequestBody LinkUpdate sendUpdateRequest) {
        sendUpdateRequest
                .getTgChatIds()
                .forEach(tgChatId ->
                        bot.execute(new SendMessage(tgChatId, sendUpdateRequest.getDescription())));
    }
}
