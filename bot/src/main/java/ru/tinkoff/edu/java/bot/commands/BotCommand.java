package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public sealed abstract class BotCommand permits
        StartCommand,
        HelpCommand,
        TrackCommand,
        UntrackCommand,
        ListCommand {
    private String command;

    private String description;

    public String getCommand() {
        return this.command;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract SendMessage handle(Update update);

    public boolean supports(Update update) {
        return update.message().text().split(" ")[0].equals(getCommand());
    }

    public String getArgument(Update update) {
        String[] strings = update.message().text().split(" ");
        if (strings.length < 2) {
            return null;
        } else {
            return strings[1];
        }
    }
}
