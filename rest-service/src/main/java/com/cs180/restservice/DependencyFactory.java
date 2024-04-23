package com.cs180.restservice;

import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.connect.ConnectClient;

/**
 * The module containing all dependencies required by the {@link Handler}.
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of ConnectClient
     */
    public static ConnectClient connectClient() {
        return ConnectClient.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();
    }
}