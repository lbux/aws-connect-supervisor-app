package com.cs180.restservice.avgHandleTime;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Constants;
import com.cs180.restservice.util.Insight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AvgHandleTimeController {
    private static final Logger logger = LoggerFactory.getLogger(AvgHandleTimeController.class);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/avghandletime")
    public Insight avgHandleTime() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();
        Double queueAvgHandleTimeValue = handler.sendRequestQueueAvgHandleTime(Constants.BASIC_QUEUE_ID);

        Insight insight = new Insight(
                counter.incrementAndGet(),
                queueAvgHandleTimeValue,
                "Display Basic Queue's Avg Handle Time",
                "N/A",
                "N/A"
        );
        return insight;
    }
}
