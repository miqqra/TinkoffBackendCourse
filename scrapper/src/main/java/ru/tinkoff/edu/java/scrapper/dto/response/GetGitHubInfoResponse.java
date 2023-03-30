package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GetGitHubInfoResponse(@JsonProperty("updated_at") OffsetDateTime lastUpdate) {
}
