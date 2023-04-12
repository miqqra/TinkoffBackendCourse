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
        BotCommand correctCommand = commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .orElse(null);
        if (correctCommand == null){
            BotCommand anyCommand = commands().get(0);
            return new SendMessage(anyCommand.getUserId(update), "Некорректная команда");
        }
        return correctCommand.handle(update);
    }

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
