package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public final class UntrackCommand extends BotCommand {
    private final String command = "/untrack";
    private final String description = "Прекратить отслеживание ссылки";


    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
