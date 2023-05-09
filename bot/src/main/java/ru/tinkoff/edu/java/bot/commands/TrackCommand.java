package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.BotService;

@Component
@RequiredArgsConstructor
public final class TrackCommand implements BotCommand {
    /**
     * Bot service.
     */
    private final BotService botService;
    /**
     * Command name.
     */
    private final String command = "/track";
    /**
     * Command description.
     */
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
    public SendMessage handle(final Update update) {
        if (getArgument(update).isEmpty()) {
            return new SendMessage(
                    getUserId(update),
                    "Не указана ссылка для отслеживания");
        }
        return new SendMessage(
                getUserId(update),
                botService.startTrackingLink(
                        getArgument(update), getUserId(update))
        );
    }
}
