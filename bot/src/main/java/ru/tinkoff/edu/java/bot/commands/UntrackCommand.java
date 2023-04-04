package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.tinkoff.edu.java.bot.service.BotService;

@Controller
@RequiredArgsConstructor
public final class UntrackCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/untrack";
    private final String description = "Прекратить отслеживание ссылки";

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
        if (getArgument(update).isEmpty()){
            return new SendMessage(userId, "Не указана ссылка для прекращения отслеживания");
        }
        return new SendMessage(
                userId,
                botService.stopTrackingLink(getArgument(update), userId));
    }
}
