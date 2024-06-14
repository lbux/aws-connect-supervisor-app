package com.cs180.restservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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

    public Source getInsightType() {
        return this.source;
    }

    public record Source(
            String sourceName,
            String sourceLink) {
    }

    public static class BedrockSourceSerializer extends JsonSerializer<BedrockSource> {
        @Override
        public void serialize(BedrockSource value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("SourceName", value.getInsightType().sourceName());
            gen.writeStringField("SourceLink", value.getInsightType().sourceLink());
            gen.writeEndObject();
        }
    }
}
