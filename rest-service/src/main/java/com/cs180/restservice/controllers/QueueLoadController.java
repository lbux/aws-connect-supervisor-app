package com.cs180.restservice.controllers;

import java.util.List;
import java.util.Optional;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QueueLoadController {
    private static final Logger logger = LoggerFactory.getLogger(QueueLoadController.class);

    @GetMapping("/queueload")
    public static Optional<Insights> getQueueLoadInsight(ConnectHandler handler) {
        logger.info("/// QUEUE LOAD CONTROLLER CALLED ///");

        Insights queueLoadInsightList = new Insights();

        for (String queueId : handler.instance.getQueues().keySet()) {
            Optional<Double> contactsInQueue = handler.sendRequestContactsInQueue(queueId);
            Optional<Double> agentsStaffed = handler.sendRequestAgentsStaffed(queueId);

            if (contactsInQueue.isPresent() && agentsStaffed.isPresent()) {
                double queueLoad = contactsInQueue.get() / agentsStaffed.get();
                String queueName = handler.instance.getQueues().get(queueId);
                if (queueLoad > 2) {
                    Insight insight = new Insight(
                            InsightType.QUEUE_LOAD,
                            queueLoad,
                            queueName + " queue is overloaded with excessive number of contacts waiting",
                            "The high contact to agent ratio (" + contactsInQueue.get() + " / " + agentsStaffed.get() + ") in the " + queueName + " queue indicates that there are too many contacts waiting to be served by the available agents. This can lead to long wait times, which can frustrate customers and increase the likelihood of call abandonment, negatively impacting customer satisfaction and sales.",
                            "To reduce wait times in the " + queueName + " queue, I recommend: 1. Increase the number of agents available to handle the incoming calls. This can be done by ensuring agents are logged in and available during their scheduled shifts, and by being flexible with shift changes to accommodate peak demand periods. 2. Streamline the call-handling process by providing agents with effective call scripts, troubleshooting guides, and other tools to help them resolve customer issues quickly and efficiently, reducing the time spent on each call.",
                            new Metadata(queueId, null, List.of(BedrockSource.TRAINING_PT1, BedrockSource.TRAINING_PT2))
                    );

                    queueLoadInsightList.insights().add(insight);
                }
            }
        }

        if (queueLoadInsightList.insights().isEmpty()) {
            logger.info("/// queueLoadInsightList is empty ///");
            return Optional.empty();
        }
        return Optional.of(queueLoadInsightList);
    }
}