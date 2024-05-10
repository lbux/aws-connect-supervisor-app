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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QueueStore:\n");
        for (int i = 0; i < queues.size(); i++) {
            sb.append("Queue ").append(i + 1).append(": ");
            sb.append(queues.get(i)).append("\n");
        }
        return sb.toString();
    }
}
