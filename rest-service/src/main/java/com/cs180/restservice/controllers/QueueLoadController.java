package com.cs180.restservice.controllers;

import java.util.Optional;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.InsightType;
import com.cs180.restservice.util.Insights;
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
                double queueLoad = contactsInQueue.get()/agentsStaffed.get();
                String queueName = handler.instance.getQueues().get(queueId);
                if (queueLoad > 2) {
                    Insight insight = new Insight(
                            InsightType.QUEUE_LOAD,
                            queueLoad,
                            queueName + " is overloaded with excessive number of contacts waiting",
                            queueName + " (ID: " + queueId + ") Queue Load is the ratio of contacts in queue to agents staffed. " +
                                    "A high queue load indicates that the ratio of contacts waiting for an agent to respond " +
                                    "to the number of agents currently available exceeds 2. " +
                                    "This implies a high contact volume and may lead to increased customer dissatisfaction and increased abandon rates. " +
                                    "Current agents may also experience difficulties with increased contact volume.",
                            "To improve queue load, consider optimizing staffing level by assigning more available agents to " + queueName + "."
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