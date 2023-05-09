package ru.tinkoff.edu.java.bot.client;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessor;


@RequiredArgsConstructor
public class BotClient {
    private final WebClient webClient;
    private final static String LINKS_REQUEST = "/links";
    private final static String TG_CHAT_ID = "tgChatId";

    public String showCommandsList() {
        return UserMessageProcessor.showAllCommands();
    }

    public Mono<ListLinksResponse> showTrackedLinks(final Long id) {
        return webClient
                .get()
                .uri(LINKS_REQUEST)
                .header(TG_CHAT_ID, id.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }

    public Mono<String> registrateUser(final Long id) {
        String path = "tg-chat/%d".formatted(id);
        return webClient
                .post()
                .uri(path)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> startTrackingLink(
            final URI newLink, final Long userId) {
        return webClient
                .post()
                .uri(LINKS_REQUEST)
                .body(BodyInserters.fromValue(new AddLinkRequest(newLink)))
                .header(TG_CHAT_ID, userId.toString())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<LinkResponse> stopTrackingLink(
            final URI link, final Long userId) {
        return webClient
                .delete()
                .uri(LINKS_REQUEST)
                .headers(httpHeaders -> {
                    httpHeaders.set(TG_CHAT_ID, userId.toString());
                    httpHeaders.set("url", link.toString());
                })
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }
}
