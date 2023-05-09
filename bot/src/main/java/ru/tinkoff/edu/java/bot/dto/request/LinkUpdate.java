package ru.tinkoff.edu.java.bot.dto.request;

import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
public class LinkUpdate {
    /**
     * Link id.
     */
    private Long id;
    /**
     * Tracked link url.
     */
    private URI url;
    /**
     * Update info.
     */
    private String description;
    /**
     * Tg ids, who followed the link.
     */
    private List<Long> tgChatIds;
}
