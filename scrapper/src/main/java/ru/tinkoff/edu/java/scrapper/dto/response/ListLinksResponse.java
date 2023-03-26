package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
