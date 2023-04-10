package ru.tinkoff.edu.java.linkparser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(classes = {LinkParser.class})
public class LinkParserTest {
    private final LinkParser linkParser = new LinkParser();

    @ParameterizedTest
    @ValueSource(strings = {
            "https://stackoverflow.com/questions/49733733/open-a-project-in-intellij-from-folder",
            "https://stackoverflow.com/questions/49733733",
            "https://stackoverflow.com/questions/49733733/"
    })
    void returnsSameStackOverflowQuestionIds(String input) throws MalformedURLException {
        ParseResult parseResult = linkParser.parse(new URL(input));
        assertThat(parseResult, is(notNullValue()));
        assertThat(parseResult, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult).getQuestionId(), equalTo(49733733L));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://ru.stackoverflow.com/questions/1510760",
            "https://ru.stackoverflow.com/questions/1510760/"
    })
    void returnsOtherSameStackOverflowQuestionIds(String input) throws MalformedURLException {
        ParseResult parseResult = linkParser.parse(new URL(input));
        assertThat(parseResult, is(notNullValue()));
        assertThat(parseResult, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult).getQuestionId(), equalTo(1510760L));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/miqqra/TinkoffBackendCourse/pull/3",
            "https://github.com/miqqra/TinkoffBackendCourse",
            "https://github.com/miqqra/TinkoffBackendCourse/"
    })
    void returnsSameGithubData(String input) throws MalformedURLException {
        ParseResult parseResult = linkParser.parse(new URL(input));
        assertThat(parseResult, is(notNullValue()));
        assertThat(parseResult, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult).getRepository(), equalTo("TinkoffBackendCourse"));

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/miqqra/OOP"
    })
    void returnsOtherGithubData(String input) throws MalformedURLException {
        ParseResult parseResult = linkParser.parse(new URL(input));
        assertThat(parseResult, is(notNullValue()));
        assertThat(parseResult, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult).getRepository(), equalTo("OOP"));

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://stackoverflow.com/questions/",
            "https://stackoverflow.com/questions",
            "https://stackoverflow.com/",
            "https://ru.stackoverflow.com",
            "https://ru.stack",
            "https://github.com/miqqra/",
            "https://github.com/",
            "https://github",
            "https://github.com/miqqra"
    })
    void parseIncorrectURLs(String input) throws MalformedURLException {
        assertThat(linkParser.parse(new URL(input)), is(nullValue()));

    }
}
