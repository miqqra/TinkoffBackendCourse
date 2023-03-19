package ru.tinkoff.edu.java.linkparser.links;

import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;

import java.net.URL;

public sealed abstract class Link permits GitHubLink, StackOverflowLink {
    public Link nextLink;

    public Link(Link nextLink) {
        this.nextLink = nextLink;
    }

    public abstract ParseResult matches(URL url);
}
