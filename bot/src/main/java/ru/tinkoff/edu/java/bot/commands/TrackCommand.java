package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.edu.java.bot.service.BotService;

@Controller
@RequiredArgsConstructor
public final class TrackCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/track";
    private final String description = "Начать отслеживание ссылки";

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
        return new SendMessage(
                getUserId(update),
                botService.startTrackingLink(getArgument(update), getUserId(update)));
    }
}
