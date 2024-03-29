package com.goit.Utils;


import com.goit.org.functionalInteface.DailyNotification;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NotificationScheduler {
    private final int hours;
    private final DailyNotification notification;
    private ScheduledFuture<?> scheduledFuture;

    public NotificationScheduler(int hours, DailyNotification notification) {
        this.hours = hours;
        this.notification = notification;
    }

    public void start() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextRun = now.withHour(hours).withMinute(0).withSecond(0);
        if (now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        System.out.println("next run = " + nextRun);

        Duration duration = Duration.between(now, nextRun);
        long initialDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduledFuture = scheduler.scheduleAtFixedRate(notification,
                initialDelay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledFuture.cancel(true);
    }
}
