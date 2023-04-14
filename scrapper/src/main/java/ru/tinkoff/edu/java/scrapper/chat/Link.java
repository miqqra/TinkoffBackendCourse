package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Link(String url) {
        this.url = url;
    }
}
