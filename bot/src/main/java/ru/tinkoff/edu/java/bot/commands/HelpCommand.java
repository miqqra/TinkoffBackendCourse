package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessorImpl;

public final class HelpCommand extends BotCommand {
    private final String command = "/help";
    private final String description = "Вывести окно с командами";

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
                update.updateId(),
                UserMessageProcessorImpl.showAllCommands()
                );
    }
}
