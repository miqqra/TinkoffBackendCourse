package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.commands.BotCommand;
import java.util.List;

public class UserMessageProcessor {
    /**
     * All tg commands.
     */
    private static List<? extends BotCommand> commands;

    /**
     * Bot command handler.
     */
    public UserMessageProcessor(final List<? extends BotCommand> commands) {
        UserMessageProcessor.commands = commands;
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
        BotCommand correctCommand = commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .orElse(null);
        if (correctCommand == null) {
            BotCommand anyCommand = commands().get(0);
            return new SendMessage(
                    anyCommand.getUserId(update), "Некорректная команда");
        }
        return correctCommand.handle(update);
    }

    /**
     * To string for commands list.
     */
    public static String showAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        commands
                .forEach(command -> stringBuilder
                        .append(command.getCommand())
                        .append(" - ")
                        .append(command.getDescription())
                        .append("\n"));
        return stringBuilder.toString();
    }
}
