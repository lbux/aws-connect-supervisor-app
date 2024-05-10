package com.cs180.restservice.insights;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class InsightsController {
    private static final Logger logger = LoggerFactory.getLogger(InsightsController.class);

    private final AtomicLong counter = new AtomicLong();

    public static Queues queues;

    @GetMapping("/insights")
    public Queues insights() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();
        Queues queues = handler.sendRequestPopulateQueueStore();

        logger.info("/// QUEUE STORE OUTPUT ///");
        logger.info(queues.toString());

        return queues;
    }
}
