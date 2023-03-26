package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
