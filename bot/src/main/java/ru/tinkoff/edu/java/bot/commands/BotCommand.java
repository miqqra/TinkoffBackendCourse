package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BotCommand {
    /**
     * Get command name.
     */
    String getCommand();

    /**
     * Get command description.
     */
    String getDescription();

    /**
     * Handle update.
     */
    SendMessage handle(Update update);

    /**
     * Check if current command can handle update.
     */
    default boolean supports(Update update) {
        if (update.message() == null || update.message().text() == null) {
            return false;
        }
        return update.message().text().split(" ")[0].equals(getCommand());
    }

    /**
     * Get argument from update.
     */
    default String getArgument(Update update) {
        String[] strings = update.message().text().split(" ");
        if (strings.length < 2) {
            return "";
        } else {
            return strings[1];
        }
    }

    /**
     * Get user tg id.
     */
    default Long getUserId(Update update) {
        if (update.message() == null) {
            return -1L;
        }
        return update.message().chat().id();
    }
}
