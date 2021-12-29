package com.ordjoy.dto;

public record UserAccountFilter(int limit,
                                int offset,
                                String email,
                                String login) {
}