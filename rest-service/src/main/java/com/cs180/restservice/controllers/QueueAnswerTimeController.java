package com.cs180.restservice.controllers;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.S3Handler;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import com.cs180.restservice.util.InsightType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class QueueAnswerTimeController {
    private static final Logger logger = LoggerFactory.getLogger(QueueAnswerTimeController.class);

    @GetMapping("/queueanswertime")
    public static Optional<Insights> getQueueAnswerTimeInsights(S3Handler s3Handler, ConnectHandler connectHandler) {
        logger.info("/// GET QUEUE ANSWER TIME FUNCTION CALLED ///");

        Insights queueAnswerTimeInsightList = new Insights();

        // queue insights
        for (String queueId : connectHandler.instance.getQueues().keySet()) {
            Optional<Double> historical_queueAnswerTimeValue = s3Handler.sendRequestAvgQueueAnswerTimeThreshold(queueId);
            Optional<Double> realtime_queueAnswerTimeValue = connectHandler.sendRequestAverageQueueAnswerTime(queueId);
            if (historical_queueAnswerTimeValue.isPresent() && realtime_queueAnswerTimeValue.isPresent()
                && realtime_queueAnswerTimeValue.get() > historical_queueAnswerTimeValue.get()) {
                Insight insight = new Insight(
                        InsightType.QUEUE_ANSWER_TIME,
                        historical_queueAnswerTimeValue.get(),
                        "Display Queue " + queueId + " Avg Answer Time",
                        "Queue " + queueId + " maps to Queue Name: " + connectHandler.instance.getQueues().get(queueId),
                        "N/A"
                );
                queueAnswerTimeInsightList.insights().add(insight);
            }

        }
        if (queueAnswerTimeInsightList.insights().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(queueAnswerTimeInsightList);
    }

}
