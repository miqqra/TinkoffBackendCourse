package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        //System.out.println("checked");
    }
}
