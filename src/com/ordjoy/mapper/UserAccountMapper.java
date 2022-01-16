package com.ordjoy.mapper;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserAccount;

public class UserAccountMapper implements Mapper<UserAccount, UserAccountDto> {

    private static final UserAccountMapper INSTANCE = new UserAccountMapper();

    private UserAccountMapper() {

    }

    public static UserAccountMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserAccountDto mapFrom(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getId(),
                userAccount.getLogin(),
                userAccount.getEmail(),
                userAccount.getDiscountPercentageLevel()
        );
    }
}