package ru.tinkoff.edu.java.linkparser.parseResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class GitHubParseResult extends ParseResult {
    private String user;
    private String repository;
}
