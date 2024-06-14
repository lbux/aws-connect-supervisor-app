package com.cs180.restservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = InsightType.InsightTypeSerializer.class)
public enum InsightType {
    SERVICE_LEVEL(new Type(1, "queue", "ServiceLevel")),
    AVG_HANDLE_TIME_QUEUE(new Type(2, "queue", "QueueAvgHandleTime")),
    AVG_HANDLE_TIME_AGENT(new Type(3, "agent", "AgentAvgHandleTime")),
    QUEUE_LOAD(new Type(4, "queue", "QueueLoad")),
    QUEUE_ANSWER_TIME(new Type(5, "queue", "QueueAnswerTime"));

    private final Type type;

    InsightType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public record Type(int id,
                       String group,
                       String name) {
    }

    public static class InsightTypeSerializer extends JsonSerializer<InsightType> {
        @Override
        public void serialize(InsightType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("id", String.valueOf(value.getType().id()));
            gen.writeStringField("group", value.getType().group());
            gen.writeStringField("name", value.getType().name());
            gen.writeEndObject();
        }
    }
}
