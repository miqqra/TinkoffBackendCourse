package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.Data;

import java.net.URI;

@Data
public class LinkResponse {
    private Long id;
    private URI url;
}
