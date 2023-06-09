package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.CommandHandler;

@Component
@RequiredArgsConstructor
public final class StartCommand implements BotCommand {
    private final CommandHandler botService;
    private final String command = "/start";
    private final String description = "Зарегистрировать пользователя";

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SendMessage handle(final Update update) {
        return new SendMessage(
                getUserId(update),
                botService.registrateUser(getUserId(update)));
    }
}
