package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @SequenceGenerator(
            name = "link_sequence",
            sequenceName = "link_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "link_sequence"
    )
    private Long id;

    private String url;
    private OffsetDateTime lastUpdated;
    private OffsetDateTime lastChecked;
    private OffsetDateTime lastCheckedWhenWasUpdated;
    private OffsetDateTime lastCommitDate;
    private OffsetDateTime lastAnswerDate;

    public Link(String url) {
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
