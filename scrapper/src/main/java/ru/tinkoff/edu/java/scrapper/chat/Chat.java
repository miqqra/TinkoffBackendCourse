package ru.tinkoff.edu.java.scrapper.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Chat {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgCharIds;

    public Chat(Long id) {
        this.id = id;
        this.description = null;
        this.url = null;
        this.tgCharIds = new ArrayList<>();
    }
}
