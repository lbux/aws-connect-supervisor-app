package com.cs180.restservice;

public record ServiceLevel(long id,
                            String instanceId,
                            double SLvalue,
                            String insightText) { }
