package com.cs180.restservice.insights;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.avgHandleTime.AvgHandleTimeController;
import com.cs180.restservice.serviceLevel.ServiceLevelController;
import com.cs180.restservice.util.ConnectInstance;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class InsightsController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsController.class);

    public static ConnectInstance instance = new ConnectInstance();

    @GetMapping("/insights")
    public Insights insights() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();

        handler.sendRequestPopulateQueues(instance);

        logger.info("/// QUEUE STORE OUTPUT ///");
        logger.info(instance.getQueues().toString());

        ArrayList<Insight> insightList = new ArrayList<>();

        // INSIGHT #1: queue's service level over 40%
        ServiceLevelController.serviceLevel(handler).ifPresent(insightList::add);

        // INSIGHT #2: an agent's avg handling time is way over their queue level over 40%
        AvgHandleTimeController.avgHandleTime(handler, instance).ifPresent(insightList::add);

        Insights insights = new Insights(insightList);

        return insights;
    }
}
