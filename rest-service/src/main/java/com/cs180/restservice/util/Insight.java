package com.cs180.restservice.util;

import java.util.concurrent.atomic.AtomicLong;

public record Insight(long id,
                      InsightType insightType,
                      Double value,
                      String insight,
                      String reason,
                      String action) {

    private static final AtomicLong counter = new AtomicLong();

    public Insight(InsightType insightType, Double value, String insight, String reason, String action) {
        this(counter.incrementAndGet(), insightType, value, insight, reason, action);
    }

    public Insight() {
        this(counter.incrementAndGet(), null, null, null, null, null);
    }
}
