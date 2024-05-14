package com.cs180.restservice;

import com.cs180.restservice.util.AgentInfo;
import com.cs180.restservice.util.Constants;
import com.cs180.restservice.util.Queues;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectHandler {
    private final ConnectClient connectClient;

    public ConnectHandler() {
        connectClient = DependencyFactory.connectClient();
    }

    public Queues sendRequestPopulateQueues() {
        return populateQueues(connectClient);
    }

    public List<String> sendRequestListInstances() {
        return listAllInstances(connectClient);
    }

    public AgentInfo sendRequestAgentInfo(String userId) {
        return describeUser(connectClient, userId);
    }

    public Optional<Double> sendRequestServiceLevel() {
        return getServiceLevel15(connectClient);
    }

    public Double sendRequestQueueAvgHandleTime(String queueId) {
        return getQueueAvgHandleTime(connectClient, queueId);
    }

    ///////// END of Send Request Methods, START of Connect Methods /////////

    /*
    IMPORTANT: only call once at start up to reduce overhead
     */
    public static Queues populateQueues(ConnectClient connectClient) {
        Queues queues = new Queues();

        try {
            ListQueuesRequest queuesRequest = ListQueuesRequest.builder()
                    .instanceId(Constants.INSTANCE_ID)
                    .queueTypes(QueueType.STANDARD)
                    .build();

            ListQueuesResponse response = connectClient.listQueues(queuesRequest);
            for (QueueSummary queue : response.queueSummaryList()) {
                queues.addQueue(queue.id(), queue.name());
            }

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }

        return queues;
    }

    public static List<String> listAllInstances(ConnectClient connectClient) {
        try {
            ListInstancesRequest instancesRequest = ListInstancesRequest.builder()
                    .maxResults(10)
                    .build();

            ListInstancesResponse response = connectClient.listInstances(instancesRequest);
            List<InstanceSummary> instances = response.instanceSummaryList();
//            String output = "";
//            for (InstanceSummary instance : instances) {
//                System.out.println("The identifier of the instance is " + instance.id());
//                System.out.println("The instance alias of the instance is " + instance.instanceAlias());
//                System.out.println("The ARN  of the instance is " + instance.arn());
//
//                output += "The identifier of the instance is " + instance.id() + "\nThe instance alias of the instance is " + instance.instanceAlias() + "\nThe ARN  of the instance is " + instance.arn() + "\n";
//            }
            InstanceSummary instance = instances.get(0);

            List<String> output = new ArrayList<>(3);
            output.add(instance.id());
            output.add(instance.instanceAlias());
            output.add(instance.arn());

            return output;

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        List<String> output = new ArrayList<>(3);
        output.add("null");
        output.add("null");
        output.add("null");
        return output;
    }

    public static AgentInfo describeUser(ConnectClient connectClient, String userId) {
        try {
            DescribeUserRequest userRequest = DescribeUserRequest.builder()
                    .instanceId(Constants.INSTANCE_ID)
                    .userId(userId)
                    .build();

            DescribeUserResponse response = connectClient.describeUser(userRequest);
            String username = response.user().username();
            String firstName = response.user().identityInfo().firstName();
            String lastName = response.user().identityInfo().lastName();

            return new AgentInfo(userId, username, firstName, lastName);

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Agent/User with ID: " + userId + " does not exist.");
            System.exit(1);
        }
        return null;
    }

    public static Optional<Double> getServiceLevel15(ConnectClient connectClient) {
        try {
            List<ThresholdV2> thresholds = new ArrayList<>(1);
            ThresholdV2 threshold = ThresholdV2.builder()
                    .comparison("LT")
                    .thresholdValue(15.0)
                    .build();
            thresholds.add(threshold);

            List<MetricV2> metrics = new ArrayList<>(1);
            MetricV2 metric = MetricV2.builder()
                    .name("SERVICE_LEVEL")
                    .threshold(thresholds)
                    .build();
            metrics.add(metric);

            List<String> filterValues = new ArrayList<>(1);
            filterValues.add("19dfef86-2020-46d3-b881-976564077825");

            List<FilterV2> filters = new ArrayList<>(1);
            FilterV2 filter = FilterV2.builder()
                    .filterKey("QUEUE")
                    .filterValues(filterValues)
                    .build();
            filters.add(filter);

            GetMetricDataV2Request metricRequest = GetMetricDataV2Request.builder()
                    .startTime(Instant.ofEpochSecond(1715647396))
                    .endTime(Instant.ofEpochSecond(1715647467))
//                    .startTime(Instant.ofEpochSecond(1712359075))
//                    .endTime(Instant.ofEpochSecond(1712445432))
                    .metrics(metrics)
                    .filters(filters)
                    .resourceArn("arn:aws:connect:us-west-2:471112891051:instance/9198298a-bdc4-4f38-9156-76e99f4c84b0")
                    .maxResults(10)
                    .build();

            GetMetricDataV2Response response = connectClient.getMetricDataV2(metricRequest);
            return response.metricResults().get(0).collections().get(0).value().describeConstable();

        } catch (ConnectException | IndexOutOfBoundsException e) {
            System.out.println(e.getLocalizedMessage());
//            System.exit(1);
        }
        return Optional.empty();
    }

    public static Double getQueueAvgHandleTime(ConnectClient connectClient, String queueId) {
        try {
            List<MetricV2> metrics = new ArrayList<>(1);
            MetricV2 metric = MetricV2.builder()
                    .name("AVG_HANDLE_TIME")
                    .build();
            metrics.add(metric);

            List<String> filterValues = new ArrayList<>(1);
            filterValues.add(queueId);

            List<FilterV2> filters = new ArrayList<>(1);
            FilterV2 filter = FilterV2.builder()
                    .filterKey("QUEUE")
                    .filterValues(filterValues)
                    .build();
            filters.add(filter);

            GetMetricDataV2Request metricRequest = GetMetricDataV2Request.builder()
                    .startTime(Instant.ofEpochSecond(1712356707))
                    .endTime(Instant.ofEpochSecond(1712443492))
                    .metrics(metrics)
                    .filters(filters)
                    .resourceArn(Constants.RESOURCE_ARN)
                    .build();

            GetMetricDataV2Response response = connectClient.getMetricDataV2(metricRequest);
            return response.metricResults().get(0).collections().get(0).value();

        } catch (ConnectException e) {
            System.out.println("getServiceLevel15 ConnectException");
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        return null;
    }
}