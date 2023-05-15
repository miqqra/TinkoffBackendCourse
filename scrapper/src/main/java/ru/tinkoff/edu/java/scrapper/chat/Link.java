package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "link")
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    @Column(name = "last_checked")
    private OffsetDateTime lastChecked;

    @Column(name = "last_checked_when_was_updated")
    private OffsetDateTime lastCheckedWhenWasUpdated;

    @Column(name = "last_commit_date")
    private OffsetDateTime lastCommitDate;

    @Column(name = "last_answer_date")
    private OffsetDateTime lastAnswerDate;

    public Link(String url) {
        this.url = url;
    }

    public Link(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Link(String url, OffsetDateTime lastUpdated, OffsetDateTime lastChecked, OffsetDateTime lastCheckedWhenWasUpdated, OffsetDateTime lastCommitDate, OffsetDateTime lastAnswerDate) {
        this.url = url;
        this.lastUpdated = lastUpdated;
        this.lastChecked = lastChecked;
        this.lastCheckedWhenWasUpdated = lastCheckedWhenWasUpdated;
        this.lastCommitDate = lastCommitDate;
        this.lastAnswerDate = lastAnswerDate;
    }
}
