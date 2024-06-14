package com.cs180.restservice.controllers;

import com.cs180.restservice.S3Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class InsightsController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsController.class);

    @GetMapping("/insights")
    public Insights insights() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler connectHandler = new ConnectHandler();
        S3Handler s3Handler = new S3Handler();
        
        logger.info("/// QUEUE STORE OUTPUT ///");
        logger.info(connectHandler.instance.getQueues().toString());

        ArrayList<Insight> insightList = new ArrayList<>();

        // INSIGHT #1: queue's service level over 40%
        ServiceLevelController.getServiceLevelQueueInsights(connectHandler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

        // INSIGHT #2: a queue or an agent's avg handling time is over 60 seconds
        // fix and change into: an agent's avg handling time is way over their queue level over 40%
        AvgHandleTimeController.getAvgHandleTimeQueueInsights(connectHandler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

        // INSIGHT #3: a queue's load (contacts waiting to agents available) ratio is greater than 2
        QueueLoadController.getQueueLoadInsight(connectHandler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

        // INSIGHT #4: a queue's current answer time is longer than historical average
        QueueAnswerTimeController.getQueueAnswerTimeInsights(s3Handler, connectHandler)
                .map(Insights::insights)
                .ifPresent(insightList::addAll);

        return new Insights(insightList);
    }
}
