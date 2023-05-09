package ru.tinkoff.edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.service.BotService;

@Component
@RequiredArgsConstructor
public final class ListCommand implements BotCommand {
    /**
     * Bot service.
     */
    private final BotService botService;
    /**
     * Command name.
     */
    private final String command = "/list";
    /**
     * Command description.
     */
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
    public SendMessage handle(final Update update) {
        Long userId = getUserId(update);
        List<LinkResponse> listLinksResponse =
                botService.showTrackedLinks(userId).links();
        String result = listLinksResponse.isEmpty()
                ? "Нет отслеживаемых ссылок"
                : prettyPrint(listLinksResponse);
        return new SendMessage(
                userId,
                result
        );
    }

    private String prettyPrint(final List<LinkResponse> linkResponses) {
        StringBuilder stringBuilder = new StringBuilder();
        linkResponses
                .forEach(linkResponse ->
                        stringBuilder.append(linkResponse.url()).append("\n"));
        return stringBuilder.toString();
    }
}
