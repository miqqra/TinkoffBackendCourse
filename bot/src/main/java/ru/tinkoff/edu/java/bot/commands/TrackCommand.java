package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public final class TrackCommand extends BotCommand {
    private final String command = "/track";
    private final String description = "Начать отслеживание ссылки";


    @Override
    public SendMessage handle(Update update) {
        return null;
    }
}
