package ru.tinkoff.edu.java.bot.dto.request;

import lombok.Data;

import java.net.URI;

@Data
public class RemoveLinkRequest {
    private URI link;
}
