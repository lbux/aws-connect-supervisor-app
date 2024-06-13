package com.cs180.restservice.controllers;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.S3Handler;
import com.cs180.restservice.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class QueueAnswerTimeController {
    private static final Logger logger = LoggerFactory.getLogger(QueueAnswerTimeController.class);

    @GetMapping("/answertime")
    public static Optional<Insights> getQueueAnswerTimeInsights(S3Handler s3Handler, ConnectHandler connectHandler) {
        logger.info("/// GET QUEUE ANSWER TIME FUNCTION CALLED ///");

        Insights queueAnswerTimeInsightList = new Insights();

        // queue insights
        for (String queueId : connectHandler.instance.getQueues().keySet()) {
            Optional<Double> historical_queueAnswerTimeValue = s3Handler.sendRequestAvgQueueAnswerTimeThreshold(queueId);
            Optional<Double> realtime_queueAnswerTimeValue = connectHandler.sendRequestAverageQueueAnswerTime(queueId);
            if (historical_queueAnswerTimeValue.isPresent() && realtime_queueAnswerTimeValue.isPresent()
                    && realtime_queueAnswerTimeValue.get() > historical_queueAnswerTimeValue.get()) {
                String queueName = connectHandler.instance.getQueues().get(queueId);
                Insight insight = new Insight(
                        InsightType.QUEUE_ANSWER_TIME,
                        historical_queueAnswerTimeValue.get(),
                        "The " + queueName + " queue response time is " + (realtime_queueAnswerTimeValue.get() * 60) + " minutes, which is longer than the normal" + (realtime_queueAnswerTimeValue.get() * 60) + " minutes",
                        "The longer " + queueName + " queue response time of " + (realtime_queueAnswerTimeValue.get() * 60) + " minutes indicates that there may be an issue with the queue's performance. This could be due to high call volume, insufficient agent staffing, or inefficient call handling processes.",
                        "As the supervisor, I recommend the following actions to address the longer queue response time: 1. Analyze the call volume and staffing levels to ensure adequate resources are available to handle the queue. 2. Provide additional training to agents on efficient call handling techniques, such as using call scripts and troubleshooting guides, to streamline conversations and reduce handling times.",
                        new Metadata(queueId, null, List.of(BedrockSource.TRAINING_PT1, BedrockSource.TRAINING_PT2)));

                queueAnswerTimeInsightList.insights().add(insight);
            }

        }
        if (queueAnswerTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(queueAnswerTimeInsightList);
    }

}
