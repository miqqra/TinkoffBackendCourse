package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RequiredArgsConstructor
public abstract class UpdateHandler {
    private final Bot bot;

    public void handleUpdates(LinkUpdateRequest linkUpdate){
        linkUpdate
                .getTgChatIds()
                .forEach(tgChatId ->
                        bot.execute(new SendMessage(tgChatId, linkUpdate.getDescription())));
    }
}
