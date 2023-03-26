package ru.tinkoff.edu.java.bot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.BotService;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @ApiOperation(value = "Отправить обновление")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление обработано"),
            @ApiResponse(code = 400, message = "Некорректные параметры запроса")
    })
    @PostMapping(value = "/updates")
    public void sendUpdate(@RequestBody LinkUpdate sendUpdateRequest) {
        botService.sendUpdate(sendUpdateRequest);
    }
}
