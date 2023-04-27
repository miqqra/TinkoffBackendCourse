package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.impl.JdbcLinkUpdater;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final JdbcLinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        linkUpdater.updateUncheckedLinks();
    }
}
