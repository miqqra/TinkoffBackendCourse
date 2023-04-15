package ru.tinkoff.edu.java.scrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.chat.Chat;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.repository.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcTgChatRepository jdbcTgChatRepository;
    private final ClientService clientService;

    @Override
    public int update() {
        int counter = 0;
        List<Chat> chatList = jdbcTgChatRepository.findAllChats();
        chatList.forEach(chat -> {
                    chat.getTrackedLinksId().forEach(link -> {
                        //todo
                        if (link.getUrl().contains("github")) {
                            GetGitHubInfoResponse getGitHubInfoResponse =
                                    clientService.getGitHubInfo("", "");
                        } else if (link.getUrl().contains("stackoverflow")) {
                            GetStackOverflowInfoResponse getStackOverflowInfoResponse =
                                    clientService.getStackOverflowInfo(-1L);
                        } else {
                        }
                    });
                }
        );
        return 0;
    }
}
