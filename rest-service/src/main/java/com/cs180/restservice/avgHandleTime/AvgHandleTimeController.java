package com.cs180.restservice.avgHandleTime;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.serviceLevel.ServiceLevelController;
import com.cs180.restservice.util.ConnectInstance;
import com.cs180.restservice.util.Constants;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AvgHandleTimeController {
    private static final Logger logger = LoggerFactory.getLogger(AvgHandleTimeController.class);

    @GetMapping("/avghandletime")
    public static Optional<Insights> avgHandleTime(ConnectHandler handler, ConnectInstance instance) {
        logger.info("/// AVG HANDLE TIME ENDPOINT CALLED ///");

        handler.sendRequestPopulateQueues(instance);

        return getAvgHandleTime(handler, instance);
    }

    public static Optional<Insights> getAvgHandleTime(ConnectHandler handler, ConnectInstance instance) {
        logger.info("/// AVG HANDLE TIME FUNCTION CALLED ///");

        Insights avgHandleTimeInsightList = new Insights();

        for (String queueId : instance.getQueues().keySet()) {
            Double queueAvgHandleTimeValue = handler.sendRequestQueueAvgHandleTime(queueId);

            Insight insight = new Insight(
                    queueAvgHandleTimeValue,
                    "Display Queue " + queueId + " Avg Handle Time",
                    "Queue " + queueId + " maps to Queue Name: " + instance.getQueues().get(queueId),
                    "N/A"
            );

            avgHandleTimeInsightList.insights().add(insight);
        }

        System.out.println(avgHandleTimeInsightList);

        System.out.println(avgHandleTimeInsightList.insights());

        if (avgHandleTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(avgHandleTimeInsightList);
    }
}
