package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Update;

public sealed class TestBotCommand permits TestListCommand {
    private String command;

    private String description;

    public String getCommand() {
        return this.command;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean supports(Update update) {
        return false;
    }
}
