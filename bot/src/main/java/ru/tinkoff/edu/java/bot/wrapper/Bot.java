package ru.tinkoff.edu.java.bot.wrapper;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class Bot {
    @Value("#{@getBotToken}")
    private final String token = null;

    private TelegramBot telegramBot;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
    }

    public int process(List<Update> updates) {
        updates.stream().forEach(System.out::println);
        return 0;
    }

    public void start() {
        telegramBot = new TelegramBot(token);
    }

    public void close() {
        telegramBot.shutdown();
    }
}
