package ru.tinkoff.edu.java.bot.controller;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RabbitListener(queues = "${app.scrapper-queue.name}")
public class ScrapperQueueListener extends UpdateHandler {

    public ScrapperQueueListener(Bot bot) {
        super(bot);
    }

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        this.handleUpdates(update);
    }
}
