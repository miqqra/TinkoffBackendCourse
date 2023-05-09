package ru.tinkoff.edu.java.scrapper.dto.request;

import java.net.URI;
import lombok.Data;

@Data
public class RemoveLinkRequest {
    private URI link;
}
