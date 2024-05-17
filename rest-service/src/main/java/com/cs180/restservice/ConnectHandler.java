package com.cs180.restservice;

import com.cs180.restservice.util.AgentInfo;
import com.cs180.restservice.util.ConnectInstance;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ConnectHandler {
    private final ConnectClient connectClient;

    public final ConnectInstance instance = new ConnectInstance();

    public ConnectHandler() {
        connectClient = DependencyFactory.connectClient();
        sendRequestPopulateQueues();
    }

    public void sendRequestPopulateQueues() {
        listAndPopulateQueues(connectClient, instance);
    }

    public List<String> sendRequestListInstances() {
        return listAllInstances(connectClient);
    }

    public AgentInfo sendRequestAgentInfo(String userId) {
        return describeUser(connectClient, userId);
    }

    public Set<String> sendRequestAgentsInQueue(String queueId) {
        return getCurrentUserData(connectClient, queueId);
    }

    public Optional<Double> sendRequestServiceLevel(String queueId) {
        return getServiceLevel15(connectClient, queueId);
    }

    public Optional<Double> sendRequestQueueAvgHandleTime(String queueId) {
        return getQueueAvgHandleTime(connectClient, queueId);
    }

    public Optional<Double> sendRequestAgentAvgHandleTime(String queueId, String agentId) {
        return getAgentInQueueAvgHandleTime(connectClient, queueId, agentId);
    }

    ///////// ^ END of Send Request Methods /////////

    public List<FilterV2> getFilters(String queueId, String agentId) {
        List<FilterV2> filters = new ArrayList<>();

        if (queueId != null) {
            FilterV2 filter1 = FilterV2.builder()
                    .filterKey("QUEUE")
                    .filterValues(List.of(queueId))
                    .build();
            filters.add(filter1);
        }

        if (agentId != null) {
            FilterV2 filter2 = FilterV2.builder()
                    .filterKey("AGENT")
                    .filterValues(List.of(agentId))
                    .build();
            filters.add(filter2);
        }
        return filters;
    }

    ///////// ^ END of Helper Methods /////////

    /*
    IMPORTANT: only call once at start up to reduce overhead
     */
    public void listAndPopulateQueues(ConnectClient connectClient, ConnectInstance instance) {
        try {
            ListQueuesRequest queuesRequest = ListQueuesRequest.builder()
                    .instanceId(instance.getInstanceId())
                    .queueTypes(QueueType.STANDARD)
                    .build();

            ListQueuesResponse response = connectClient.listQueues(queuesRequest);
            for (QueueSummary queue : response.queueSummaryList()) {
                if (!instance.getQueues().containsKey(queue.id())) {
                    instance.addQueue(queue.id(), queue.name());
                } else {
                    System.out.println("Instance Queue List already contains Queue " + queue.id());
                }
            }

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    public List<String> listAllInstances(ConnectClient connectClient) {
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

    public AgentInfo describeUser(ConnectClient connectClient, String userId) {
        try {
            DescribeUserRequest userRequest = DescribeUserRequest.builder()
                    .instanceId(instance.getInstanceId())
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

    public Set<String> getCurrentUserData(ConnectClient connectClient, String queueId) {
        try {
            UserDataFilters filters = UserDataFilters.builder()
                    .queues(Collections.singleton(queueId))
                    .build();

            GetCurrentUserDataRequest userRequest = GetCurrentUserDataRequest.builder()
                    .instanceId(instance.getInstanceId())
                    .filters(filters)
                    .build();

            GetCurrentUserDataResponse response = connectClient.getCurrentUserData(userRequest);

            return response.userDataList().stream()
                    .map(userData -> userData.user().id())
                    .collect(Collectors.toSet());

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public Optional<Double> getServiceLevel15(ConnectClient connectClient, String queueId) {
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

            GetMetricDataV2Request metricRequest = GetMetricDataV2Request.builder()
//                    .startTime(Instant.ofEpochSecond(1715647396)) // for empty metric results
//                    .endTime(Instant.ofEpochSecond(1715647467))
                    .startTime(Instant.ofEpochSecond(1712359075)) // for present metric results
                    .endTime(Instant.ofEpochSecond(1712445432))
                    .metrics(metrics)
                    .filters(getFilters(queueId, null))
                    .resourceArn("arn:aws:connect:us-west-2:471112891051:instance/9198298a-bdc4-4f38-9156-76e99f4c84b0")
                    .maxResults(10)
                    .build();

            GetMetricDataV2Response response = connectClient.getMetricDataV2(metricRequest);
            if (!response.metricResults().isEmpty()) {
                return Optional.of(response.metricResults().get(0).collections().get(0).value());
            }

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public Optional<Double> getQueueAvgHandleTime(ConnectClient connectClient, String queueId) {
        try {
            List<MetricV2> metrics = new ArrayList<>(1);
            MetricV2 metric = MetricV2.builder()
                    .name("AVG_HANDLE_TIME")
                    .build();
            metrics.add(metric);

            GetMetricDataV2Request metricRequest = GetMetricDataV2Request.builder()
                    .startTime(Instant.ofEpochSecond(1712356707))
                    .endTime(Instant.ofEpochSecond(1712443492))
                    .metrics(metrics)
                    .filters(getFilters(queueId, null))
                    .resourceArn(instance.getResourceArn())
                    .build();

            GetMetricDataV2Response response = connectClient.getMetricDataV2(metricRequest);
            if (!response.metricResults().isEmpty()) {
                return Optional.of(response.metricResults().get(0).collections().get(0).value());
            }

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public Optional<Double> getAgentInQueueAvgHandleTime(ConnectClient connectClient, String queueId, String agentId) {
        try {
            List<MetricV2> metrics = new ArrayList<>(1);
            MetricV2 metric = MetricV2.builder()
                    .name("AVG_HANDLE_TIME")
                    .build();
            metrics.add(metric);

            GetMetricDataV2Request metricRequest = GetMetricDataV2Request.builder()
                    .startTime(Instant.ofEpochSecond(1712356707))
                    .endTime(Instant.ofEpochSecond(1712443492))
                    .metrics(metrics)
                    .filters(getFilters(queueId, agentId))
                    .resourceArn(instance.getResourceArn())
                    .build();

            GetMetricDataV2Response response = connectClient.getMetricDataV2(metricRequest);
            if (!response.metricResults().isEmpty()) {
                return Optional.of(response.metricResults().get(0).collections().get(0).value());
            }

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return Optional.empty();
    }
}