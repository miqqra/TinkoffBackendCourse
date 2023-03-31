package ru.tinkoff.edu.java.bot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
public class LinkResponse {
    private Long id;
    private URI url;
}
