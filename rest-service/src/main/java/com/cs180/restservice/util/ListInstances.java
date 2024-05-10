package com.cs180.restservice.util;

public record ListInstances(long id,
                            String instanceId,
                            String instanceAlias,
                            String instanceARN) {
}
