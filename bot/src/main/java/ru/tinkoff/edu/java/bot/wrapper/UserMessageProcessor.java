package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.commands.BotCommand;

import java.util.List;

public class UserMessageProcessor {
    private static List<? extends BotCommand> commands;

    public UserMessageProcessor(List<? extends BotCommand> commands) {
        this.commands = commands;
    }

    public List<? extends BotCommand> commands() {
        return commands;
    }

    public SendMessage process(Update update) {
        return commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .map(command -> command.handle(update))
                .orElse(null);
    }

    public static String showAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        commands
                .stream()
                .forEach(command -> stringBuilder
                        .append(command.getCommand())
                        .append(" - ")
                        .append(command.getDescription())
                        .append("\n"));
        return stringBuilder.toString();
    }
}
