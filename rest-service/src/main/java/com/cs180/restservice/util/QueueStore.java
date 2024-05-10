package com.cs180.restservice.util;

import java.util.ArrayList;

public class QueueStore {
    private ArrayList<Queue> queues;

    public QueueStore() {
        queues = new ArrayList<>();
    }

    public void addQueue(Queue queue) {
        queues.add(queue);
    }
}
