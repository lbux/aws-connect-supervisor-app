package com.cs180.restservice.util;

import java.util.HashMap;

public class Queues {
    private HashMap<String, String> queues; // key: queueId, value: queueName

    public Queues() {
        queues = new HashMap<>();
    }

    public void addQueue(String id, String name) {
        queues.put(id, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Queues:\n");
        for (HashMap.Entry<String, String> entry : queues.entrySet()) {
            sb.append("Queue ID: ").append(entry.getKey()).append(", Name: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
