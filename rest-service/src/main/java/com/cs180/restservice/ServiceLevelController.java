package com.cs180.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class ServiceLevelController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLevelController.class);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/servicelevel")
    public ServiceLevel servicelevel() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        Handler handler = new Handler();
        List<String> output = handler.sendRequestServiceLevel();

        return new ServiceLevel(
                counter.incrementAndGet(),
                output.get(0),
                output.get(1),
                output.get(2)
        );
    }
}
