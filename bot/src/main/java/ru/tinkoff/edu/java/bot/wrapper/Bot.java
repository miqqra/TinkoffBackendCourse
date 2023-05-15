package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.commands.StartCommand;
import ru.tinkoff.edu.java.bot.commands.TrackCommand;
import ru.tinkoff.edu.java.bot.commands.UntrackCommand;

@Component
@RequiredArgsConstructor
public class Bot {
    @Value("#{@getBotToken}")
    private final String token = null;
    private TelegramBot telegramBot;
    private UserMessageProcessor userMessageProcessor;
    private final HelpCommand helpCommand;
    private final ListCommand listCommand;
    private final StartCommand startCommand;
    private final TrackCommand trackCommand;
    private final UntrackCommand untrackCommand;

    public <T extends BaseRequest<T, R>, R extends BaseResponse>
    void execute(final BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    public void process(final List<Update> updates) {
        List<SendMessage> sendMessages = updates
                .stream()
                .map(update -> userMessageProcessor.process(update))
                .toList();
        sendMessages.forEach(this::execute);
    }

    public void start() {
        telegramBot = new TelegramBot(token);
        userMessageProcessor = new UserMessageProcessor(
                List.of(helpCommand, listCommand,
                        startCommand, trackCommand, untrackCommand
                )
        );

        telegramBot.setUpdatesListener((updates) -> {
            this.process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void close() {
        telegramBot.shutdown();
    }
}
