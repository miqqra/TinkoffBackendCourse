package ru.tinkoff.edu.java.bot.dto.request;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddLinkRequest {
    private URI link;
}
