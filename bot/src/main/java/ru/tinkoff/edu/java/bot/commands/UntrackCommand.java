package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.service.BotService;

@RequiredArgsConstructor
public final class UntrackCommand extends BotCommand {
    private final BotService botService;
    private final String command = "/untrack";
    private final String description = "Прекратить отслеживание ссылки";


    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.updateId(), botService.stopTrackingLink());
    }

    private String getLink(Update update) {
        return botService.stopTrackingLink();
    }
}
