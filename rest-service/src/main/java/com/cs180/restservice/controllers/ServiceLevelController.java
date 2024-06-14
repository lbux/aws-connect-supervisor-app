package com.cs180.restservice.controllers;

import com.cs180.restservice.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.ConnectHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ServiceLevelController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLevelController.class);

    @GetMapping("/servicelevel")
    public static Optional<Insights> getServiceLevelQueueInsights(ConnectHandler handler) {
        logger.info("/// SERVICE LEVEL CONTROLLER CALLED ///");

        Insights serviceLevelInsightList = new Insights();

        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> SL15 = handler.sendRequestServiceLevel(queueId);

            checkAndAddInsight(handler, serviceLevelInsightList, queueId, SL15);
        }

        if (serviceLevelInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceLevelInsightList);
    }

    @GetMapping("/servicelevel-test")
    public static Optional<Insights> getTestServiceLevelQueueInsights(ConnectHandler handler) {
        logger.info("/// GET TEST SERVICE LEVEL ENDPOINT CALLED ///");

        Insights serviceLevelInsightList = new Insights();

        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> SL15 = handler.sendRequestTestServiceLevel(queueId);

            checkAndAddInsight(handler, serviceLevelInsightList, queueId, SL15);
        }

        if (serviceLevelInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceLevelInsightList);
    }

    private static void checkAndAddInsight(ConnectHandler handler, Insights serviceLevelInsightList, String queueId, Optional<Double> SL15) {
        if (SL15.isPresent()) {
            double SL15value = SL15.get();
            String queueName = handler.instance.getQueues().get(queueId);
            if (SL15value <= 40) {
                Insight insight = new Insight(
                        InsightType.SERVICE_LEVEL,
                        SL15value,
                        "Service Level 15 for " + queueName + " has dropped below 40%",
                        "The low service level for " + queueName + " indicates that too many contacts are waiting in the queue, resulting in long wait times for customers. This can lead to increased customer frustration and higher call abandonment rates, negatively impacting customer satisfaction and the overall performance of the contact center.",
                        "To address this issue, I recommend: 1. Ensure that you have the appropriate number of agents staffed and available to handle incoming calls, especially during peak times. 2. Work with your team to efficiently handle each call, using call scripts and troubleshooting guides to streamline conversations and avoid lengthy, off-topic discussions.",
                        new Metadata(queueId, null, List.of(BedrockSource.TRAINING_PT1))
                );

                serviceLevelInsightList.insights().add(insight);
            }
        }
    }
}
