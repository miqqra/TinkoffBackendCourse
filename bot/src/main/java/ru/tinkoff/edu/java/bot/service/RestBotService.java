package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.controller.UpdateHandler;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

public class RestBotService extends UpdateHandler {

    public RestBotService(Bot bot, BotClient botClient) {
        super(bot, botClient);
    }

    @Override
    public void receiver(LinkUpdateRequest update) {
        this.handleUpdates(update);
    }
}
