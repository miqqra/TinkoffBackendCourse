package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Chat {
    @Id
    @SequenceGenerator(
            name = "chat_sequence",
            sequenceName = "chat_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "chat_sequence"
    )
    private Long id;

    private Long tgChatId;

    @OneToMany
    private List<Link> trackedLinksId;

    public Chat() {
        trackedLinksId = new ArrayList<>();
    }

    public Chat(Long tgChatId, List<Link> trackedLinksId) {
        this.tgChatId = tgChatId;
        this.trackedLinksId = trackedLinksId;
    }

    public boolean addTrackedLink(Link link) {
        return trackedLinksId.add(link);
    }
}
