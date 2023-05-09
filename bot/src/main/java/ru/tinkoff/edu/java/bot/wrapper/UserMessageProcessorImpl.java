package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.commands.BotCommand;

import java.util.List;

public class UserMessageProcessorImpl {
    private static List<? extends BotCommand> commands;

    /**
     * Bot command handler implementation.
     */
    public UserMessageProcessorImpl(final List<? extends BotCommand> commands) {
        this.commands = commands;
    }

    /**
     * Get commands.
     */
    public List<? extends BotCommand> commands() {
        return commands;
    }

    /**
     * Handle updates.
     */
    public SendMessage process(final Update update) {
        var a = commands
            .stream()
            .filter(command -> command.supports(update))
            .findAny();
        if (a.isPresent()) {
            return a.get().handle(update);
        } else {
            return null;
        }
    }

    /**
     * To string for commands list.
     */
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
