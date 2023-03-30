package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class GetStackOverflowInfoResponse {
    OffsetDateTime lastActivityDate;

    @JsonProperty("items")
    private void unpackNameFromNestedObject(List<Map<String, String>> items) {
        lastActivityDate = OffsetDateTime.parse(items.get(0).get("last_activity_date"));
    }
}

