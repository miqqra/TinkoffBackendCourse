package ru.tinkoff.edu.java.bot.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.tinkoff.edu.java.bot.test.client.TestBotClient;

@Profile("test")
@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    public TestBotClient webClient() {
        return new TestBotClient();
    }
}
