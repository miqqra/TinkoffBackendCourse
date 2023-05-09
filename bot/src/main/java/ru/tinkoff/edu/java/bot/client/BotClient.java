package ru.tinkoff.edu.java.bot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.wrapper.UserMessageProcessor;

import java.net.URI;

@RequiredArgsConstructor
public class BotClient {
    /**
     * Web client to connect with scrapper.
     */
    private final WebClient webClient;

    /**
     * Show commands list.
     */
    public String showCommandsList() {
        return UserMessageProcessor.showAllCommands();
    }

    /**
     * Show tracked links.
     */
    public Mono<ListLinksResponse> showTrackedLinks(final Long id) {
        String path = "/links";
        return webClient
            .get()
            .uri(path)
            .header("tgChatId", id.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class);
    }

    /**
     * Registrate user.
     */
    public Mono<String> registrateUser(final Long id) {
        String path = "tg-chat/%d".formatted(id);
        return webClient
            .post()
            .uri(path)
            .retrieve()
            .bodyToMono(String.class);
    }

    /**
     * Track new link.
     */
    public Mono<String> startTrackingLink(
        final URI newLink, final Long userId) {
        String path = "/links";
        return webClient
            .post()
            .uri(path)
            .body(BodyInserters.fromValue(new AddLinkRequest(newLink)))
            .header("tgChatId", userId.toString())
            .retrieve()
            .bodyToMono(String.class);
    }

    /**
     * Stop track link.
     */
    public Mono<LinkResponse> stopTrackingLink(
        final URI link, final Long userId) {
        String path = "/links";
        return webClient
            .delete()
            .uri(path)
            .headers(httpHeaders -> {
                httpHeaders.set("tgChatId", userId.toString());
                httpHeaders.set("url", link.toString());
            })
            .retrieve()
            .bodyToMono(LinkResponse.class);
    }
}
