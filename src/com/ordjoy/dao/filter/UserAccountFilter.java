package com.ordjoy.dao.filter;

import com.ordjoy.entity.UserRole;

public record UserAccountFilter(int limit,
                                int offset,
                                UserRole role) implements Filter {
}