package com.ordjoy.filter;

public record UserAccountFilter(int limit,
                                int offset,
                                String email,
                                String login) {
}