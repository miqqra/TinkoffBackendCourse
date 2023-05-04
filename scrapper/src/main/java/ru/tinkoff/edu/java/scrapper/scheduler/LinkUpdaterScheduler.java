package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    Logger logger = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int updatedLinksNumber;
        try {
            updatedLinksNumber = linkUpdater.updateUncheckedLinks();
        } catch (Exception e) {
            logger.error("API недоступен " + e.getMessage());
            return;
        }
        logger.info(updatedLinksNumber + " links updated");
    }
}
