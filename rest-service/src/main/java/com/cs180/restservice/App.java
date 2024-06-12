package com.cs180.restservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String... args) {
        // removes Logger DEBUG output
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.INFO);

        logger.info("Application starts");

//        System.out.println("AWS_ACCESS_KEY_ID: " + System.getenv("AWS_ACCESS_KEY_ID"));
//        System.out.println("AWS_SECRET_ACCESS_KEY: " + System.getenv("AWS_SECRET_ACCESS_KEY"));
//        System.out.println("AWS_PROFILE: " + System.getenv("AWS_PROFILE"));


        S3Handler handler = new S3Handler();
        handler.listBuckets();

        System.out.println();

        String queueId = "19dfef86-2020-46d3-b881-976564077825";
        handler.getAvgQueueAnswerTimeThreshold(queueId);

        System.out.println();

        String agentId = "89d84d36-16fb-4799-95f2-e565e60f8f64";
        handler.getAgentRecentSentimentScore(agentId);

        logger.info("Application ends");
    }
}