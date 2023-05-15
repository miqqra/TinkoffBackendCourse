package ru.tinkoff.edu.java.bot.dto.request;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveLinkRequest {
    /**
     * Tracked link url.
     */
    private URI link;
}
