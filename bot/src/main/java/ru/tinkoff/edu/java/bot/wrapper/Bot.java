package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
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
    /**
     * Bot token.
     */
    @Value("#{@getBotToken}")
    private final String token = null;

    /**
     * Tg bot.
     */
    private TelegramBot telegramBot;

    /**
     * Tg bot functionality realisation.
     */
    private UserMessageProcessor userMessageProcessor;

    /**
     * Bot help command.
     */
    private final HelpCommand helpCommand;

    /**
     * Bot list command.
     */
    private final ListCommand listCommand;

    /**
     * Bot start command.
     */
    private final StartCommand startCommand;

    /**
     * Bot track command.
     */
    private final TrackCommand trackCommand;

    /**
     * Bot untrack command.
     */
    private final UntrackCommand untrackCommand;

    /**
     * Execute request.
     *
     * @param request Request.
     * @param <T>     Response type.
     * @param <R>     Request type.
     */
    public <T extends BaseRequest<T, R>, R extends BaseResponse>
    void execute(final BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    /**
     * @param updates Updates list.
     */
    public void process(final List<Update> updates) {
        List<SendMessage> sendMessages = updates
            .stream()
            .map(update -> userMessageProcessor.process(update))
            .toList();
        sendMessages.forEach(this::execute);
    }

    /**
     * Start bot.
     */
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

    /**
     * Shutdown bot.
     */
    public void close() {
        telegramBot.shutdown();
    }
}
