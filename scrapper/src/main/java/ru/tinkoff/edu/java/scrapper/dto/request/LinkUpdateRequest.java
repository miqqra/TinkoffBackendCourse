package ru.tinkoff.edu.java.scrapper.dto.request;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
