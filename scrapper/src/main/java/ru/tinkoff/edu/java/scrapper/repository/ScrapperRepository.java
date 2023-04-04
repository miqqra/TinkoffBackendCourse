package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.chat.Chat;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ScrapperRepository {
    List<Chat> chats = new ArrayList<>();


    public boolean add(Long id) {
        if (chats.stream().anyMatch(x -> x.getId().equals(id)))
            return false;
        chats.add(new Chat(id));
        return true;
    }

    public boolean delete(Long id) {
        for (Chat chat : chats) {
            if (chat.getId().equals(id)) {
                chats.remove(chat);
                return true;
            }
        }
        return false;
    }

    public List<Chat> findAll() {
        return chats;
    }
}
