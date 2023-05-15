package ru.tinkoff.edu.java.bot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class GetStackOverflowInfoResponse {
    private OffsetDateTime lastActivityDate;

    @JsonProperty("items")
    private void unpackNameFromNestedObject(
            final List<Map<String, String>> items) {
        lastActivityDate = OffsetDateTime.parse(
                items.get(0).get("last_activity_date"));
    }
}

