package ru.tinkoff.edu.java.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
