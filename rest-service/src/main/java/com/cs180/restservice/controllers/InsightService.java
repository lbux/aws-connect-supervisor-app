package com.cs180.restservice.controllers;

import com.cs180.restservice.util.BedrockSource;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.InsightType;
import com.cs180.restservice.util.Insights;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Service
public class InsightService {

//    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectMapper mapper = configureMapper();

    public ArrayList<Insight> loadInsightsFromFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/" + filePath);

        if (inputStream != null) {
            return mapper.readValue(inputStream, new TypeReference<ArrayList<Insight>>() {});
        } else {
            throw new IOException("Resource not found: " + filePath);
        }
    }

    public static ObjectMapper configureMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());

        SimpleModule module = new SimpleModule();
        module.addDeserializer(InsightType.class, new InsightType.InsightTypeDeserializer());
        module.addDeserializer(BedrockSource.class, new BedrockSource.BedrockSourceDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}
