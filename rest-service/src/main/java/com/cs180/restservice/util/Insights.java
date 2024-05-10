package com.cs180.restservice.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public record Insights(String id,
                       String timestamp,
                       List<Insight> insights) {

    private static final AtomicLong counter = new AtomicLong();

    public Insights(List<Insight> insights) {
        this("IL-" + counter.incrementAndGet(), getFormattedTimestamp(), insights);
    }

    private static String getFormattedTimestamp() {
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("America/Los_Angeles"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
        return zonedDateTime.format(formatter);
    }
}
