package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Update;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserMessageProcessorTest {
    List<TestBotCommand> commands = new ArrayList<>();

    @Test
    void testNoCorrectCommand() {
        Update update = null;
        commands.add(new TestListCommand());

        String ans = null;
        TestBotCommand correctCommand = commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .orElse(null);
        if (correctCommand == null) {
            ans = "Некорректная команда";
        }

        assertThat(ans, is(notNullValue()));
        assertThat(ans, equalTo("Некорректная команда"));
    }
}
