package com.cs180.restservice.serviceLevel;

public record ServiceLevel(long id,
                           Double value,
                           String insight,
                           String reason,
                           String action) { }
