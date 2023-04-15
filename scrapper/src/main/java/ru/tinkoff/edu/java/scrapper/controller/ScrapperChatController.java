package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RestController
@RequiredArgsConstructor
public class ScrapperChatController {
    private final TgChatService tgChatService;

    @PostMapping(value = "/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        tgChatService.register(id);
    }


    @DeleteMapping(value = "/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        tgChatService.unregister(id);
    }

}
