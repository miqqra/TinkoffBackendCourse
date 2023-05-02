package ru.tinkoff.edu.java.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RestController
public class BotController extends UpdateHandler {
    public BotController(Bot bot) {
        super(bot);
    }

    @PostMapping(value = "/updates")
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        this.handleUpdates(linkUpdateRequest);
    }
}
