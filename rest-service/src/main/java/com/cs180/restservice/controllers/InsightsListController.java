package com.cs180.restservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class InsightsListController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsListController.class);

    @Autowired
    private InsightService insightService;

    @CrossOrigin(origins = "http://localhost:53140")
    @GetMapping("/insights-list")
    public Optional<Insights> insightsList() {
        Insights insights = new Insights();
        try {
            insights.insights().addAll(insightService.loadInsightsFromFile("src/main/resources/insights.json"));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(insights);
    }
}
