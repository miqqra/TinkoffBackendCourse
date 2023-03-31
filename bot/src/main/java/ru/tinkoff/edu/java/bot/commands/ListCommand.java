package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.service.BotService;

@RequiredArgsConstructor
public final class ListCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/list";
    private final String description = "Показать список отслеживаемых ссылок";

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
                update.updateId(),
                botService.showTrackedLinks()
        );
    }
}
