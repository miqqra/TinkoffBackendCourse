package ru.tinkoff.edu.java.scrapper.chat;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @Id
    @OneToMany
    private Long id;

    private URI url;
}
