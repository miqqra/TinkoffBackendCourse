package ru.tinkoff.edu.java.bot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
public class RemoveLinkRequest {
    /**
     * Tracked link url.
     */
    private URI link;
}
