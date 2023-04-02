package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.commands.StartCommand;
import ru.tinkoff.edu.java.bot.commands.TrackCommand;
import ru.tinkoff.edu.java.bot.commands.UntrackCommand;

import java.util.List;

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

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        System.out.println("execute");
        telegramBot.execute(request);
    }

    public int process(List<Update> updates) {
        updates.forEach(update -> userMessageProcessor.process(update));
        updates
                .stream()
                .map(update -> userMessageProcessor.process(update))
                .map(sendMessage -> telegramBot.execute(sendMessage));
        return 0;
    }

    public void start() {
        System.out.println("start");
        telegramBot = new TelegramBot(token);
        userMessageProcessor = new UserMessageProcessor(
                List.of(helpCommand, listCommand, startCommand, trackCommand, untrackCommand)
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
