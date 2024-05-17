package com.cs180.restservice.avgHandleTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AvgHandleTimeController {
    private static final Logger logger = LoggerFactory.getLogger(AvgHandleTimeController.class);

    @GetMapping("/avghandletime")
    public static Optional<Insights> getAvgHandleTimeQueueInsights(ConnectHandler handler) {
        logger.info("/// GET AVG HANDLE TIME FUNCTION CALLED ///");

        Insights avgHandleTimeInsightList = new Insights();

        // queue insights
        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> queueAvgHandleTimeValue = handler.sendRequestQueueAvgHandleTime(queueId);

            if (queueAvgHandleTimeValue.isPresent() && queueAvgHandleTimeValue.get() > 60) {
                Insight insight = new Insight(
                        queueAvgHandleTimeValue.get(),
                        "Display Queue " + queueId + " Avg Handle Time",
                        "Queue " + queueId + " maps to Queue Name: " + handler.instance.getQueues().get(queueId),
                        "N/A"
                );
                avgHandleTimeInsightList.insights().add(insight);
            }

            for (String agentId : handler.sendRequestAgentsInQueue(queueId)) {
                Optional<Double> agentAvgHandleTimeValue = handler.sendRequestAgentAvgHandleTime(queueId, agentId);

                if (agentAvgHandleTimeValue.isPresent() && agentAvgHandleTimeValue.get() > 60) {
                    Insight insight = new Insight(
                            agentAvgHandleTimeValue.get(),
                            "Display Agent " + agentId + " in " + handler.instance.getQueues().get(queueId) + " Avg Handle Time",
                            "Agent Details: " + handler.sendRequestAgentInfo(agentId),
                            "N/A"
                    );
                    avgHandleTimeInsightList.insights().add(insight);
                }
            }
        }

        if (avgHandleTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(avgHandleTimeInsightList);
    }
}
