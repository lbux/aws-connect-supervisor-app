package com.cs180.restservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = InsightType.InsightTypeSerializer.class)
public enum InsightType {
    SERVICE_LEVEL(new Type(1, "Queue", "ServiceLevel")),
    AVG_HANDLE_TIME_QUEUE(new Type(2, "Queue", "QueueAvgHandleTime")),
    AVG_HANDLE_TIME_AGENT(new Type(3, "Agent", "AgentAvgHandleTime")),
    QUEUE_LOAD(new Type(4, "Queue", "QueueLoad")),
    QUEUE_ANSWER_TIME(new Type(5, "Queue", "QueueAnswerTime")),
    AGENT_SENTIMENT(new Type(6, "Agent", "AgentSentiment"));

    private final Type type;

    InsightType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public static InsightType fromType(Type type) {
        for (InsightType insightType : values()) {
            if (insightType.type.equals(type)) {
                return insightType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
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

    public static class InsightTypeDeserializer extends JsonDeserializer<InsightType> {
        @Override
        public InsightType deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            int id = node.get("id").asInt();
            String group = node.get("group").asText();
            String name = node.get("name").asText();

            return InsightType.fromType(new Type(id, group, name));
        }
    }
}
