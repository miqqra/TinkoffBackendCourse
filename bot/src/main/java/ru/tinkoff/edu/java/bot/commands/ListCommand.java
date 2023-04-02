package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.edu.java.bot.service.BotService;

@Controller
@RequiredArgsConstructor
public final class ListCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/list";
    private final String description = "Показать список отслеживаемых ссылок";

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
        System.out.println("list");
        return new SendMessage(
                update.updateId(),
                botService.showTrackedLinks(Long.parseLong(getArgument(update)))
        );
    }
}
