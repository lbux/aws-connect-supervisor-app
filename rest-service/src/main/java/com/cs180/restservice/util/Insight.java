package com.cs180.restservice.util;

import java.util.concurrent.atomic.AtomicLong;

public record Insight(long id,
                      Double value,
                      String insight,
                      String reason,
                      String action) {

    private static final AtomicLong counter = new AtomicLong();

    public Insight(Double value, String insight, String reason, String action) {
        this(counter.incrementAndGet(), value, insight, reason, action);
    }

    public Insight() {
        this(counter.incrementAndGet(), null, null, null, null);
    }
}
