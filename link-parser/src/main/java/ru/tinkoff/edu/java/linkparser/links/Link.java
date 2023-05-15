package ru.tinkoff.edu.java.linkparser.links;

import java.net.URI;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;

public sealed abstract class Link permits GitHubLink, StackOverflowLink {
    public Link nextLink;

    public Link(Link nextLink) {
        this.nextLink = nextLink;
    }

    public abstract ParseResult matches(URI url);
}
