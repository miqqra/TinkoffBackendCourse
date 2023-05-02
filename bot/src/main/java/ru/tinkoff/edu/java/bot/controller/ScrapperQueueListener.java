package ru.tinkoff.edu.java.bot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.client.BotClient;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RabbitListener(queues = "${app.scrapper-queue.name}")
public class ScrapperQueueListener extends UpdateHandler {
    Logger logger = LoggerFactory.getLogger(ScrapperQueueListener.class);

    public ScrapperQueueListener(Bot bot, BotClient botClient) {
        super(bot, botClient);
    }

    @Override
    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        logger.info("update " + update);
        this.handleUpdates(update);
    }
}
