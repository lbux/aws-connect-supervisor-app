package com.cs180.restservice.insights;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.serviceLevel.ServiceLevelController;
import com.cs180.restservice.util.Insight;
import com.cs180.restservice.util.Insights;
import com.cs180.restservice.util.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class InsightsController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsController.class);

    private final AtomicLong counter = new AtomicLong();

    public static Queues queues;

    @GetMapping("/insights")
    public Insights insights() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();
        queues = handler.sendRequestPopulateQueueStore();

        logger.info("/// QUEUE STORE OUTPUT ///");
        logger.info(queues.toString());

        ArrayList<Insight> insightList = new ArrayList<>();

        insightList.add(ServiceLevelController.serviceLevel());

        Insights insights = new Insights(counter.incrementAndGet(), insightList);

        return insights;
    }
}
