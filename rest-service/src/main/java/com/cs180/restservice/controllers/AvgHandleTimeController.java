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
public class AvgHandleTimeController {
    private static final Logger logger = LoggerFactory.getLogger(AvgHandleTimeController.class);

    @GetMapping("/avghandletime")
    public static Optional<Insights> getAvgHandleTimeQueueInsights(ConnectHandler handler) {
        logger.info("/// GET AVG HANDLE TIME FUNCTION CALLED ///");

        Insights avgHandleTimeInsightList = new Insights();

        // queue insights
        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> queueAvgHandleTimeValue = handler.sendRequestAvgHandleTime(queueId, null);
            String queueName = handler.instance.getQueues().get(queueId);

            if (queueAvgHandleTimeValue.isPresent()) {
                checkAndAddQueueAHTInsight(avgHandleTimeInsightList, queueId, queueAvgHandleTimeValue.get(), queueName);

                for (String agentId : handler.sendRequestAgentsInQueue(queueId)) {
                    Optional<Double> agentAvgHandleTimeValue = handler.sendRequestAvgHandleTime(queueId, agentId);
                    String agentFirstName = handler.sendRequestAgentInfo(agentId).firstName();
                    if (agentAvgHandleTimeValue.isPresent()) {
                        checkAndAddAgentAHTInsight(agentFirstName, avgHandleTimeInsightList, queueId, queueAvgHandleTimeValue.get(), queueName, agentId, agentAvgHandleTimeValue.get());
                    }
                }
            }
        }

        if (avgHandleTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(avgHandleTimeInsightList);
    }

    @GetMapping("/avghandletime-test")
    public static Optional<Insights> getTestAvgHandleTimeQueueInsights(ConnectHandler handler) {
        logger.info("/// GET TEST AVG HANDLE TIME FUNCTION CALLED ///");

        Insights avgHandleTimeInsightList = new Insights();

        // queue insights
        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> queueAvgHandleTimeValue = handler.sendRequestTestAvgHandleTime(queueId, null);
            String queueName = handler.instance.getQueues().get(queueId);

            if (queueAvgHandleTimeValue.isPresent()) {
                checkAndAddQueueAHTInsight(avgHandleTimeInsightList, queueId, queueAvgHandleTimeValue.get(), queueName);

                for (String agentId : handler.sendRequestAgentsInQueue(queueId)) {
                    Optional<Double> agentAvgHandleTimeValue = handler.sendRequestTestAvgHandleTime(queueId, agentId);
                    String agentFirstName = handler.sendRequestAgentInfo(agentId).firstName();
                    if (agentAvgHandleTimeValue.isPresent()) {
                        checkAndAddAgentAHTInsight(agentFirstName, avgHandleTimeInsightList, queueId, queueAvgHandleTimeValue.get(), queueName, agentId, agentAvgHandleTimeValue.get());
                    }
                }
            }

        }

        if (avgHandleTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(avgHandleTimeInsightList);
    }

    private static void checkAndAddAgentAHTInsight(String agentFirstName, Insights avgHandleTimeInsightList, String queueId, Double queueAvgHandleTimeValue, String queueName, String agentId, Double agentAvgHandleTimeValue) {
        if (agentAvgHandleTimeValue > queueAvgHandleTimeValue) {
            Insight insight = new Insight(InsightType.AVG_HANDLE_TIME_AGENT, agentAvgHandleTimeValue, "Agent " + agentFirstName + "’s average handling time is " + (agentAvgHandleTimeValue * 60) + " minutes, which is much higher than the " + queueName + "’s average handling time of " + (queueAvgHandleTimeValue * 60) + " minutes", "The reason for Agent " + agentFirstName + "'s high average handling time could be that she is not resolving customer issues efficiently. She may be taking too long to identify the customer's problem, not using available resources and tools effectively, or getting sidetracked during the call.", "To address this issue, I would recommend that you: 1. Provide additional training to Agent " + agentFirstName + " on call resolution techniques and time management. 2. Monitor her calls more closely and provide feedback on how to streamline her call-handling process.", new Metadata(queueId, agentId, List.of(BedrockSource.TRAINING_PT2, BedrockSource.TRAINING_PT3)));

            avgHandleTimeInsightList.insights().add(insight);
        }
    }

    private static void checkAndAddQueueAHTInsight(Insights avgHandleTimeInsightList, String queueId, Double queueAvgHandleTimeValue, String queueName) {
        // todo: replace magic number
        if (queueAvgHandleTimeValue > 60) {
            Insight insight = new Insight(InsightType.AVG_HANDLE_TIME_QUEUE, queueAvgHandleTimeValue, queueName + " queue's average handling time is " + (queueAvgHandleTimeValue * 60) + " minutes, which is higher than targeted average of 1 minute", "The reason for the increase in the " + queueName + " queue's average handling time from 1 minute to " + (queueAvgHandleTimeValue * 60) + " minutes could be that agents are taking longer to resolve customer issues or are getting distracted during calls. This can lead to longer wait times for " + queueName + " customers, which can negatively impact their satisfaction and perception of the contact center's service quality.", "To address this issue, I recommend that you: 1. Provide additional training to agents on efficient call handling techniques and customer service best practices. 2. Implement a quality assurance program to monitor and provide feedback on agent performance in the " + queueName + " queue.", new Metadata(queueId, null, List.of(BedrockSource.TRAINING_PT1, BedrockSource.TRAINING_PT2)));
            avgHandleTimeInsightList.insights().add(insight);
        }
    }
}
