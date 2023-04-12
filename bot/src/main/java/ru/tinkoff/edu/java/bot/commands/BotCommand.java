package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface BotCommand {

    String getCommand();
    String getDescription();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update.message().text().split(" ")[0].equals(getCommand());
    }

    default String getArgument(Update update) {
        String[] strings = update.message().text().split(" ");
        if (strings.length < 2) {
            return "";
        } else {
            return strings[1];
        }
    }

    default Long getUserId(Update update){
        return update.message().chat().id();
    }
}
