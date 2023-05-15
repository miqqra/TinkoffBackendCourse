package ru.tinkoff.edu.java.scrapper.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
