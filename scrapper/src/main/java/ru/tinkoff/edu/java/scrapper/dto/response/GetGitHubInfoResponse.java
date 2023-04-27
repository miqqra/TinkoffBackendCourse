package ru.tinkoff.edu.java.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GetGitHubInfoResponse(OffsetDateTime updated_at) {
}
