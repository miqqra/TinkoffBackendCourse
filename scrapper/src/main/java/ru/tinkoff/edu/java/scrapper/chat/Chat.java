package ru.tinkoff.edu.java.scrapper.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private Long id;

    private Long tgChatId;

    private List<Link> trackedLinksId;
}
