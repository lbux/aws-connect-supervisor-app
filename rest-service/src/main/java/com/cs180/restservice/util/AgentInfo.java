package com.cs180.restservice.util;

public record AgentInfo(String id,
                        String username,
                        String firstName,
                        String lastName,
                        String status) {

    public AgentInfo(String id, String username, String firstName, String lastName) {
        this(id, username, firstName, lastName, null);
    }
}
