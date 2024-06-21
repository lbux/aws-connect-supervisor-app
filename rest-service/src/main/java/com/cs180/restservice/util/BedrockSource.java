package com.cs180.restservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = BedrockSource.BedrockSourceSerializer.class)
public enum BedrockSource {
    TRAINING_PT1(new Source("AgentTrainingManual_Pt1.pdf", "https://us-west-2.console.aws.amazon.com/s3/buckets/training-manual-bucket/AgentTrainingManual_Pt1.pdf?region=us-west-2")),
    TRAINING_PT2(new Source("AgentTrainingManual_Pt2.pdf", "https://us-west-2.console.aws.amazon.com/s3/buckets/training-manual-bucket/AgentTrainingManual_Pt2.pdf?region=us-west-2")),
    TRAINING_PT3(new Source("AgentTrainingManual_Pt3.pdf", "https://us-west-2.console.aws.amazon.com/s3/buckets/training-manual-bucket/AgentTrainingManual_Pt3.pdf?region=us-west-2"));

    private final Source source;

    BedrockSource(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return this.source;
    }

    public static BedrockSource fromType(BedrockSource.Source source) {
        for (BedrockSource bedrockSource : values()) {
            if (bedrockSource.source.equals(source)) {
                return bedrockSource;
            }
        }
        throw new IllegalArgumentException("Unknown source: " + source);
    }

    public record Source(
            String sourceName,
            String sourceLink) {
    }

    public static class BedrockSourceSerializer extends JsonSerializer<BedrockSource> {
        @Override
        public void serialize(BedrockSource value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("SourceName", value.getSource().sourceName());
            gen.writeStringField("SourceLink", value.getSource().sourceLink());
            gen.writeEndObject();
        }
    }

    public static class BedrockSourceDeserializer extends JsonDeserializer<BedrockSource> {
        @Override
        public BedrockSource deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            String SourceName = node.get("SourceName").asText();
            String SourceLink = node.get("SourceLink").asText();

            return BedrockSource.fromType(new BedrockSource.Source(SourceName, SourceLink));
        }
    }
}
