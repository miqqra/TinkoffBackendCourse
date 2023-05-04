package ru.tinkoff.edu.java.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@RabbitListener(queues = "${app.rabbitMQNames.queue-name}")
public class ScrapperQueueListener extends UpdateHandler {
    Logger logger = LoggerFactory.getLogger(ScrapperQueueListener.class);

    public ScrapperQueueListener(Bot bot) {
        super(bot);
    }

    @Override
    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        logger.info("update " + update);
        this.handleUpdates(update);
    }

    @RabbitListener(queues = "${app.rabbitMQNames.queue-name}.dlq")
    public void processFailedMessagesRequeue(Message message) {
        logger.warn("Failed to handle update: " + message);
    }
}
