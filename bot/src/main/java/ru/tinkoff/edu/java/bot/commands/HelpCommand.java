package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.BotService;

@Component
@RequiredArgsConstructor
public final class HelpCommand implements BotCommand {
    private final BotService botService;
    private final String command = "/help";
    private final String description = "Вывести окно с командами";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = getUserId(update);
        return new SendMessage(
            userId,
            botService.showCommandsList()
        );
    }
}
