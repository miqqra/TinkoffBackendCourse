package ru.tinkoff.edu.java.linkparser.links;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.tinkoff.edu.java.linkparser.parseResult.GitHubParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;

public final class GitHubLink extends Link {
    private static final String REGEX = "^https://github\\.com/([a-zA-Z0-9]+)/([a-zA-Z0-9-]+)(/.*)?";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public GitHubLink(Link nextLink) {
        super(nextLink);
    }

    @Override
    public ParseResult matches(URI url) {
        Matcher matcher = PATTERN.matcher(url.toString());
        if (matcher.matches()) {
            return new GitHubParseResult(matcher.group(1), matcher.group(2));
        } else if (nextLink != null) {
            return nextLink.matches(url);
        }
        return null;
    }
}
