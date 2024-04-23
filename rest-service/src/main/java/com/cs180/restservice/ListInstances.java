package com.cs180.restservice;

public record ListInstances(long id,
                            String instanceId,
                            String instanceAlias,
                            String instanceARN) { }
