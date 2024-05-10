package com.cs180.restservice.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record Insights(long id,
                       List<Insight> insights,
                       String timestamp) {

    public Insights(long id, List<Insight> insights) {
        this(id, insights, getFormattedTimestamp());
    }

    private static String getFormattedTimestamp() {
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("America/Los_Angeles"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a z");
        return zonedDateTime.format(formatter);
    }
}
