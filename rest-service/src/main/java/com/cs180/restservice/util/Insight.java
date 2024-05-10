package com.cs180.restservice.util;

public record Insight(long id,
                      Double value,
                      String insight,
                      String reason,
                      String action) {
}
