package com.cs180.restservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;

public class S3Handler {
    private final S3Client s3Client;

    public S3Handler() {
        s3Client = DependencyFactory.s3Client();
    }

    public void listBuckets() {
        listBuckets(s3Client);
    }

    public void getAvgQueueAnswerTimeThreshold(String queueId) {
        getAvgQueueAnswerTimeThreshold(s3Client, queueId);
    }

    public void getAgentRecentSentimentScore(String agentId) {
        getAgentRecentSentimentScore(s3Client, agentId);
    }

    private void listBuckets(S3Client s3Client) {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<Bucket> bucketList = response.buckets();
            bucketList.forEach(bucket -> {
                System.out.println("Bucket Name: " + bucket.name());
            });

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private void getAvgQueueAnswerTimeThreshold(S3Client s3Client, String queueId) {
        String bucketName = "metricsoutputbucket";
        String key = "thresholds.json";

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            JsonNode queues = jsonNode.get("queues");
            for (JsonNode queue : queues) {
                if (queue.get("queue_id").asText().equals(queueId)) {
                    double avgQueueAnswerTime = queue.get("metrics").get("AVG_QUEUE_ANSWER_TIME").asDouble();
                    System.out.println("AVG_QUEUE_ANSWER_TIME for Queue " + queueId + ": " + avgQueueAnswerTime);
                    return;
                }
            }

            System.out.println("Queue ID not found: " + queueId);

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            System.exit(1);
        }
    }

    private void getAgentRecentSentimentScore(S3Client s3Client, String agentId) {
        String bucketName = "metricsoutputbucket";
        String key = agentId + "/" + agentId + "_average.json";

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            double averageValue = jsonNode.get("average_value").asDouble();
            System.out.println("Recent Sentiment Score Value for Agent " + agentId + ": " + averageValue);

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            System.exit(1);
        }
    }
}

