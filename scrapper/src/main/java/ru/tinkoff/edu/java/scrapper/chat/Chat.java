package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "chat")
@AllArgsConstructor
public class Chat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "tgchatid")
    private Long tgChatId;

    @OneToMany(fetch = FetchType.LAZY)
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

    public boolean deleteTrackingLink(Link link) {
        return trackedLinksId.remove(link);
    }
}
