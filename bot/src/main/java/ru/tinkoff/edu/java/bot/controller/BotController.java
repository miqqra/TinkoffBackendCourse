package ru.tinkoff.edu.java.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UpdateHandler;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final UpdateHandler updateHandler;

    @PostMapping(value = "/updates")
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        updateHandler.receiver(linkUpdateRequest);
    }
}
