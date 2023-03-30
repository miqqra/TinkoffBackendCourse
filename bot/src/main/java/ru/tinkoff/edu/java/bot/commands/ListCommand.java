package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public final class ListCommand extends BotCommand {
    private final String command = "/list";
    private final String description = "Показать список отслеживаемых ссылок";

    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
