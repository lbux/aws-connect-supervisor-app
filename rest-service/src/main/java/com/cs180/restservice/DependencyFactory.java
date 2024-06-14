package com.cs180.restservice;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.connect.ConnectClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * The module containing all dependencies required by the {@link ConnectHandler}.
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of ConnectClient
     */
    public static ConnectClient connectClient() {
        return ConnectClient.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .credentialsProvider(ProfileCredentialsProvider.create("AdministratorAccess-471112891051"))
                .build();
    }

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
                .httpClientBuilder(ApacheHttpClient.builder())
                .credentialsProvider(ProfileCredentialsProvider.create("AdministratorAccess-471112891051"))
                .build();
    }
}