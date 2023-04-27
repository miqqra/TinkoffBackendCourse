package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GetGitHubCommitResponse(GitHubCommits[] commits) {
    public record GitHubCommits(GitHubCommitAuthor author, String message){}

    public record GitHubCommitAuthor(String name, String email, OffsetDateTime date){}
}
