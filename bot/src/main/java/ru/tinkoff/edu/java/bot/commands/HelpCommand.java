package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.service.BotService;

@RequiredArgsConstructor
public final class HelpCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/help";
    private final String description = "Вывести окно с командами";

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
                update.updateId(),
                botService.showCommandsList()
        );
    }
}
