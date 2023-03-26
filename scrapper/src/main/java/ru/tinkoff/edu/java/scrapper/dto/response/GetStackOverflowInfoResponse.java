package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GetStackOverflowInfoResponse {
    String sha;
    String node_id;
    String commit;
    OffsetDateTime date;

}
