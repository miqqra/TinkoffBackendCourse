package ru.tinkoff.edu.java.linkparser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(classes = {LinkParser.class})
public class LinkParserTest {

    @Test
    void parseCorrectStackoverflowURL() throws MalformedURLException {
        LinkParser linkParser = new LinkParser();
        ParseResult parseResult1 = linkParser.parse(
                new URL("https://stackoverflow.com/questions/49733733/open-a-project-in-intellij-from-folder"));
        ParseResult parseResult2 = linkParser.parse(
                new URL("https://stackoverflow.com/questions/49733733"));
        ParseResult parseResult3 = linkParser.parse(
                new URL("https://stackoverflow.com/questions/49733733/"));
        ParseResult parseResult4 = linkParser.parse(
                new URL("https://ru.stackoverflow.com/questions/1510760"));
        ParseResult parseResult5 = linkParser.parse(
                new URL("https://ru.stackoverflow.com/questions/1510760/"));

        assertThat(parseResult1, is(notNullValue()));
        assertThat(parseResult1, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult1).getQuestionId(), equalTo(49733733L));

        assertThat(parseResult2, is(notNullValue()));
        assertThat(parseResult2, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult2).getQuestionId(), equalTo(49733733L));

        assertThat(parseResult3, is(notNullValue()));
        assertThat(parseResult3, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult3).getQuestionId(), equalTo(49733733L));

        assertThat(parseResult4, is(notNullValue()));
        assertThat(parseResult4, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult4).getQuestionId(), equalTo(1510760L));

        assertThat(parseResult5, is(notNullValue()));
        assertThat(parseResult5, is(instanceOf(StackOverflowParseResult.class)));
        assertThat(((StackOverflowParseResult) parseResult5).getQuestionId(), equalTo(1510760L));
    }

    @Test
    void parseCorrectGitHubURL() throws MalformedURLException {
        LinkParser linkParser = new LinkParser();
        ParseResult parseResult1 = linkParser.parse(
                new URL("https://github.com/miqqra/TinkoffBackendCourse/pull/3"));
        ParseResult parseResult2 = linkParser.parse(
                new URL("https://github.com/miqqra/TinkoffBackendCourse"));
        ParseResult parseResult3 = linkParser.parse(
                new URL("https://github.com/miqqra/OOP"));
        ParseResult parseResult4 = linkParser.parse(
                new URL("https://github.com/miqqra/TinkoffBackendCourse/"));

        assertThat(parseResult1, is(notNullValue()));
        assertThat(parseResult1, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult1).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult1).getRepository(), equalTo("TinkoffBackendCourse"));

        assertThat(parseResult2, is(notNullValue()));
        assertThat(parseResult2, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult2).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult2).getRepository(), equalTo("TinkoffBackendCourse"));

        assertThat(parseResult3, is(notNullValue()));
        assertThat(parseResult3, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult3).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult3).getRepository(), equalTo("OOP"));

        assertThat(parseResult4, is(notNullValue()));
        assertThat(parseResult4, is(instanceOf(GitHubParseResult.class)));
        assertThat(((GitHubParseResult) parseResult4).getUser(), equalTo("miqqra"));
        assertThat(((GitHubParseResult) parseResult4).getRepository(), equalTo("TinkoffBackendCourse"));
    }

    @Test
    void parseIncorrectURLs() throws MalformedURLException {
        LinkParser linkParser = new LinkParser();
        ParseResult parseResult1 = linkParser.parse(
                new URL("https://stackoverflow.com/questions/"));
        ParseResult parseResult2 = linkParser.parse(
                new URL("https://stackoverflow.com/questions"));
        ParseResult parseResult3 = linkParser.parse(
                new URL("https://stackoverflow.com/"));
        ParseResult parseResult4 = linkParser.parse(
                new URL("https://ru.stackoverflow.com"));
        ParseResult parseResult5 = linkParser.parse(
                new URL("https://ru.stack"));
        ParseResult parseResult6 = linkParser.parse(
                new URL("https://github.com/miqqra/"));
        ParseResult parseResult7 = linkParser.parse(
                new URL("https://github.com/"));
        ParseResult parseResult8 = linkParser.parse(
                new URL("https://github"));
        ParseResult parseResult9 = linkParser.parse(
                new URL("https://github.com/miqqra"));

        assertThat(parseResult1, is(nullValue()));
        assertThat(parseResult2, is(nullValue()));
        assertThat(parseResult3, is(nullValue()));
        assertThat(parseResult4, is(nullValue()));
        assertThat(parseResult5, is(nullValue()));
        assertThat(parseResult6, is(nullValue()));
        assertThat(parseResult7, is(nullValue()));
        assertThat(parseResult8, is(nullValue()));
        assertThat(parseResult9, is(nullValue()));
    }
}
