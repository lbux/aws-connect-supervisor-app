package com.cs180.restservice;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.connect.model.ConnectException;
import software.amazon.awssdk.services.connect.model.InstanceSummary;
import software.amazon.awssdk.services.connect.model.ListInstancesRequest;
import software.amazon.awssdk.services.connect.model.ListInstancesResponse;
import java.util.List;

public class Handler {
    private final ConnectClient connectClient;

    public Handler() {
        connectClient = DependencyFactory.connectClient();
    }

    public String sendRequest() {
        // TODO: invoking the api calls using connectClient.
        return listAllInstances(connectClient);
    }

    public static String listAllInstances(ConnectClient connectClient) {
        try {
            ListInstancesRequest instancesRequest = ListInstancesRequest.builder()
                    .maxResults(10)
                    .build();

            ListInstancesResponse response = connectClient.listInstances(instancesRequest);
            List<InstanceSummary> instances = response.instanceSummaryList();
            String output = "";
            for (InstanceSummary instance : instances) {
                System.out.println("The identifier of the instance is " + instance.id());
                System.out.println("The instance alias of the instance is " + instance.instanceAlias());
                System.out.println("The ARN  of the instance is " + instance.arn());

                output += "The identifier of the instance is " + instance.id() + "\nThe instance alias of the instance is " + instance.instanceAlias() + "\nThe ARN  of the instance is " + instance.arn() + "\n";
            }

            return output;

        } catch (ConnectException e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }
        return "error";
    }
}