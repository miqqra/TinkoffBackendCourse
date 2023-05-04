package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

public class RestBotService extends UpdateHandler {

    public RestBotService(Bot bot) {
        super(bot);
    }

    @Override
    public void receiver(LinkUpdateRequest update) {
        this.handleUpdates(update);
    }
}
