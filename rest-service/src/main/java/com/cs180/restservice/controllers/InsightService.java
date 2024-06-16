package com.cs180.restservice.controllers;

import com.cs180.restservice.util.Insight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Service
public class InsightService {

    private final ObjectMapper mapper = new ObjectMapper();

    public ArrayList<Insight> loadInsightsFromFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/" + filePath);

        if (inputStream != null) {
            return mapper.readValue(inputStream, new TypeReference<ArrayList<Insight>>() {});
        } else {
            throw new IOException("Resource not found: " + filePath);
        }
    }
}
