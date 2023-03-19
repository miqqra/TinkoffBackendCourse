package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.links.GitHubLink;
import ru.tinkoff.edu.java.linkparser.links.Link;
import ru.tinkoff.edu.java.linkparser.links.StackOverflowLink;
import ru.tinkoff.edu.java.linkparser.parseResult.ParseResult;

import java.net.URL;

public class LinkParser {
    public LinkParser() {
    }

    private static Link getChainOfLinksMatched() {
        Link gitHubLink = new GitHubLink(null);
        return new StackOverflowLink(gitHubLink);
    }

    public ParseResult parse(URL url) {
        Link linksChain = getChainOfLinksMatched();
        return linksChain.matches(url);
    }
}
