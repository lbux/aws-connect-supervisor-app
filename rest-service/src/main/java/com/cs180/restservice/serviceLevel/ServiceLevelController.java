package com.cs180.restservice.serviceLevel;

import java.util.concurrent.atomic.AtomicLong;

import com.cs180.restservice.ConnectHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ServiceLevelController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLevelController.class);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/servicelevel")
    public ServiceLevel servicelevel() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();
        Double SL15value = handler.sendRequestServiceLevel();

        ServiceLevel sl;
        if (SL15value <= 40) {
            sl = new ServiceLevel(
                    counter.incrementAndGet(),
                    SL15value,
                    "Service Level 15 for Basic Queue has dropped below 40%",
                    "Basic Queue (ID: 19dfef86-2020-46d3-b881-976564077825) Service Level 15 " +
                            "(percentage of contacts answered within past 15 seconds) " +
                            "has dropped below 40%. Low answer rate indicates low efficiency " +
                            "and could lead to increased customer dissatisfaction, increased abandon rates. " +
                            "Current agents may also experience difficulties with increased contact volume.",
                    "To improve SL 15, consider optimizing staffing level by assigning more available agents to Basic queue."
            );
        } else {
            sl = new ServiceLevel(
                    counter.incrementAndGet(),
                    null,
                    null,
                    null,
                    null
            );
        }
        return sl;
    }
}
