package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetGitHubInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.GetStackOverflowInfoResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.service.ScrapperService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ScrapperController {
    private final ScrapperService scrapperService;

    @ApiOperation(value = "Зарегистрировать чат")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат зарегистрирован"),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса", response = ApiErrorResponse.class)
    })
    @PostMapping(value = "/tg-chat/{id}")
    public void registerChat(@PathVariable Long id) {
        scrapperService.registerChat(id);
    }


    @ApiOperation(value = "Удалить чат")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Чат успешно удалён"),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Чат не существует", response = ApiErrorResponse.class)
    })
    @DeleteMapping(value = "/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        scrapperService.deleteChat(id);
    }

    @ApiOperation(value = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ссылки успешно получены", response = ListLinksResponse.class),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса", response = ApiErrorResponse.class)
    })
    @GetMapping("/links")
    public ListLinksResponse getAllTrackedLinks(@RequestHeader Long tgChatId) {
        return scrapperService.getAllTrackedLinks(tgChatId);
    }

    @ApiOperation(value = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ссылка успешно добавлена", response = LinkResponse.class),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса", response = ApiErrorResponse.class)
    })
    @PostMapping("/links")
    public LinkResponse addTrackedLink(
            @RequestBody AddLinkRequest addLinkRequest,
            @RequestHeader Long tgChatId) {
        return scrapperService.addTrackedLink(addLinkRequest, tgChatId);
    }

    @ApiOperation(value = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ссылка успешно убрана", response = LinkResponse.class),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "Ссылка не найдена", response = ApiErrorResponse.class)
    })
    @DeleteMapping("/links")
    public LinkResponse deleteTrackedLink(
            @RequestHeader Long tgChatId,
            @RequestBody RemoveLinkRequest removeLinkRequest) {
        return scrapperService.deleteTrackedLink(tgChatId, removeLinkRequest);
    }

    @GetMapping(value = "/stackoverflow/api")
    public GetStackOverflowInfoResponse getStackOverflowInfo(
            @RequestParam Long id) {
        return scrapperService
                .getStackOverflowInfo(id);
    }

    @GetMapping("/github/api")
    public GetGitHubInfoResponse getGitHubInfo(
            @RequestParam String username, @RequestParam String repo) {
        return scrapperService.getGitHubInfo(username, repo);
    }

}
