package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public final class StartCommand extends BotCommand {
    private final String command = "/start";
    private final String description = "Зарегистрировать пользователя";

    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
