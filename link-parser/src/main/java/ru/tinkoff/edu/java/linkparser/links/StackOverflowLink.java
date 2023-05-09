package ru.tinkoff.edu.java.linkparser.links;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;
import ru.tinkoff.edu.java.linkparser.parseResult.StackOverflowParseResult;

public final class StackOverflowLink extends Link {
    private static final String REGEX = "^https://(ru\\.)?stackoverflow\\.com/questions/([0-9]+)(/.*)?";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public StackOverflowLink(Link nextLink) {
        super(nextLink);
    }

    @Override
    public ParseResult matches(URI url) {
        Matcher matcher = PATTERN.matcher(url.toString());
        if (matcher.matches()) {
            return new StackOverflowParseResult(Long.parseLong(matcher.group(2)));
        } else if (nextLink != null) {
            return nextLink.matches(url);
        }
        return null;
    }
}
