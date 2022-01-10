package com.ordjoy.filter;

import com.ordjoy.entity.UserAccount;

public record ReviewFilter(int limit,
                           int offset,
                           String reviewText,
                           UserAccount userAccount) implements Filter {
}