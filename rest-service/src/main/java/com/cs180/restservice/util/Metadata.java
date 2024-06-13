package com.cs180.restservice.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public record Metadata(String QueueId,
                       String AgentId,
                       List<BedrockSource> BedrockSources) {

    public Metadata(String QueueId, String AgentId, List<BedrockSource> BedrockSources) {
        this.QueueId = QueueId;
        this.AgentId = AgentId;
        this.BedrockSources = BedrockSources;
    }
}
