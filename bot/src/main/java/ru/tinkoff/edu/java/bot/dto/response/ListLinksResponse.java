package ru.tinkoff.edu.java.bot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
