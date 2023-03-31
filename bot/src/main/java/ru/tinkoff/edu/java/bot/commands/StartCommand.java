package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.service.BotService;

@RequiredArgsConstructor
public final class StartCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/start";
    private final String description = "Зарегистрировать пользователя";

    @Override
    public SendMessage handle(Update update) {
        botService.registrateUser(update.updateId());
        return null;
    }
}
