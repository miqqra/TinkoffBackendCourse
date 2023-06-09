package ru.tinkoff.edu.java.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.wrapper.Bot;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    private BotApplication() {
    }

    public static void main(final String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        Bot bot = ctx.getBean(Bot.class);
        ctx.getBean(Bot.class).start();
    }
}
