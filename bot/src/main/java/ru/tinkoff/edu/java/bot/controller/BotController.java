package ru.tinkoff.edu.java.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.BotService;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @PostMapping(value = "/updates")
    public void sendUpdate(@RequestBody LinkUpdate sendUpdateRequest) {
        botService.sendUpdate(sendUpdateRequest);
    }
}
