package com.cs180.restservice.queueLoad;

import java.util.Optional;

import com.cs180.restservice.ConnectHandler;
import com.cs180.restservice.util.Insight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QueueLoadController {
    private static final Logger logger = LoggerFactory.getLogger(QueueLoadController.class);

    @GetMapping("/queueload")
    public static Optional<Insight> getQueueLoadInsight(ConnectHandler handler) {
        logger.info("/// QueueLoadController.java ///");
//        Optional<Double> queueLoad = handler.sendRequestQueueLoad();
//
//        if (queueLoad.isPresent()) {
//            Double queueLoadValue = queueLoad.get();
//            if (queueLoadValue >= 0.5) {
//                return Optional.of(new Insight(
//                        queueLoadValue,
//                        "Queue Load for Basic Queue has exceeded 50%",
//                        "Basic Queue (ID: 19dfef86-2020-46d3-b881-976564077825) Queue Load " +
//                                "(percentage of contacts waiting in queue) " +
//                                "(percentage of contacts waiting in queue) " +
//            }
//        }
        return Optional.empty();
    }
}