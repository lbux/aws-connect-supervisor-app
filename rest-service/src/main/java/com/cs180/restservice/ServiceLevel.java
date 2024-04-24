package com.cs180.restservice;

public record ServiceLevel(long id,
                            String instanceId,
                            String SLvalue,
                            String insightText) { }
