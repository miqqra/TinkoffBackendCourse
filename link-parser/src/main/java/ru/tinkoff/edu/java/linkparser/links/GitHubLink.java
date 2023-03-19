package ru.tinkoff.edu.java.linkparser.links;

import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubLink extends Link {
    private static final String regex = "^https://github\\.com/([a-zA-Z0-9]+)/([a-zA-Z0-9-]+).*";
    private static final Pattern pattern = Pattern.compile(regex);

    public GitHubLink(Link nextLink) {
        super(nextLink);
    }

    @Override
    public ParseResult matches(URL url) {
        Matcher matcher = pattern.matcher(url.toString());
        if (matcher.matches()) {
            return new GitHubParseResult(matcher.group(1), matcher.group(2));
        } else if (nextLink != null) {
            return nextLink.matches(url);
        }
        return null;
    }
}
