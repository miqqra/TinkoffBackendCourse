import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;

import java.net.MalformedURLException;
import java.net.URL;

public class Test {
    public static void printRes(ParseResult parseResult) {
        switch (parseResult) {
            case GitHubParseResult g -> System.out.println("User:" + g.getUser() + " Repo:" + g.getRepository());
            case StackOverflowParseResult s -> System.out.println("ID:" + s.getQuestionId());
            case null -> System.out.println("Unable to parse");
            default -> {
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        LinkParser linkParser = new LinkParser();

        ParseResult parseResult = linkParser
                .parse(new URL("https://github.com/miqqra/OOP/blob/master/Task_1_2_3/build.gradle"));
        printRes(parseResult);


        parseResult = linkParser
                .parse(new URL("https://stackoverflow.com/questions/19457114/java-overriding-static-variable-of-parent-class"));
        printRes(parseResult);


        parseResult = linkParser
                .parse(new URL("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        printRes(parseResult);


        parseResult = linkParser
                .parse(new URL("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://stackoverflow.com/search?q=unsupported%20link"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://ru.stackoverflow.com/questions/1506469/%d0%9a%d0%b0%d0%ba-%d0%be%d1%81%d1%82%d0%b0%d0%bd%d0%be%d0%b2%d0%b8%d1%82%d1%8c-%d1%86%d0%b8%d0%ba%d0%bb-%d0%b2-android-studio"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://github.com/pulls/assigned"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://github.com/marketplace/codefactor"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://www.jetbrains.com/help/idea/maven-support.html#maven_multi_module"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://github.com/miqqra/"));
        printRes(parseResult);

        parseResult = linkParser
                .parse(new URL("https://github.com/miqqra"));
        printRes(parseResult);
    }
}
