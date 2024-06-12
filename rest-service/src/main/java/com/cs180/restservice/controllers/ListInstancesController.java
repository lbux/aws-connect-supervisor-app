package com.cs180.restservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.ListInstances;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

@RestController
public class ListInstancesController {
    private static final Logger logger = LoggerFactory.getLogger(ListInstancesController.class);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/listinstances")
    public ListInstances listInstances() {

        logger.info("/// TESTING LOGGER OUTPUT ///");

        ConnectHandler handler = new ConnectHandler();
        List<String> output = handler.sendRequestListInstances();

        return new ListInstances(
                counter.incrementAndGet(),
                output.get(0),
                output.get(1),
                output.get(2)
        );
    }
}
