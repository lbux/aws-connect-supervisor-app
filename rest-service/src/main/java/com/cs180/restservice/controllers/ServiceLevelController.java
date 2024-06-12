package com.cs180.restservice.controllers;

import com.cs180.restservice.util.InsightType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

            if (SL15.isPresent()) {
                double SL15value = SL15.get();
                String queueName = handler.instance.getQueues().get(queueId);
                if (SL15value <= 40) {
                    Insight insight = new Insight(
                            InsightType.SERVICE_LEVEL,
                            SL15value,
                            "Service Level 15 for " + queueName + " has dropped below 40%",
                            queueName + " (ID: " + queueId + ") Service Level 15 has dropped below 40%. " +
                                    "SL 15 is the percentage of contacts answered within past 15 seconds). " +
                                    "Low answer rate indicates low efficiency " +
                                    "and could lead to increased customer dissatisfaction, increased abandon rates. " +
                                    "Current agents may also experience difficulties with increased contact volume.",
                            "To improve SL 15, consider optimizing staffing level by assigning more available agents to " + queueName + "."
                    );

                    serviceLevelInsightList.insights().add(insight);
                }
            }
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

            if (SL15.isPresent()) {
                double SL15value = SL15.get();
                String queueName = handler.instance.getQueues().get(queueId);
                if (SL15value <= 40) {
                    Insight insight = new Insight(
                            InsightType.SERVICE_LEVEL,
                            SL15value,
                            "Service Level 15 for " + queueName + " has dropped below 40%",
                            queueName + " (ID: " + queueId + ") Service Level 15 has dropped below 40%. " +
                                    "SL 15 is the percentage of contacts answered within past 15 seconds). " +
                                    "Low answer rate indicates low efficiency " +
                                    "and could lead to increased customer dissatisfaction, increased abandon rates. " +
                                    "Current agents may also experience difficulties with increased contact volume.",
                            "To improve SL 15, consider optimizing staffing level by assigning more available agents to " + queueName + "."
                    );

                    serviceLevelInsightList.insights().add(insight);
                }
            }
        }

        if (serviceLevelInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceLevelInsightList);
    }
}
