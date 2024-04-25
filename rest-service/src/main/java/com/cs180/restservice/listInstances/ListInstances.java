package com.cs180.restservice.listInstances;

public record ListInstances(long id,
                            String instanceId,
                            String instanceAlias,
                            String instanceARN) { }
