package com.cs180.restservice.util;

import java.util.HashMap;

public class ConnectInstance {

    private final String resourceArn = "arn:aws:connect:us-west-2:471112891051:instance/9198298a-bdc4-4f38-9156-76e99f4c84b0";
    private final String instanceId = "9198298a-bdc4-4f38-9156-76e99f4c84b0";
    private HashMap<String, String> queues; // key: queueId, value: queueName

    public ConnectInstance() {
        queues = new HashMap<>();
    }

    public HashMap<String, String> getQueues() {
        return queues;
    }

    public void addQueue(String id, String name) {
        queues.put(id, name);
    }

    public String printQueues() {
        StringBuilder sb = new StringBuilder();
        sb.append("Queues:\n");
        if (queues.isEmpty()) {
            sb.append("No queues available\n");
        } else {
            for (HashMap.Entry<String, String> entry : queues.entrySet()) {
                sb.append("Queue ID: ").append(entry.getKey()).append(", Name: ").append(entry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}
