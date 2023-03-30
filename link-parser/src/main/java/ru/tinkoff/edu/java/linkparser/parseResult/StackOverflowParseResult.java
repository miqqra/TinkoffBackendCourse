package ru.tinkoff.edu.java.linkparser.parseResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class StackOverflowParseResult extends ParseResult {
    private long questionId;
}
