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
        return UserAccountDto.builder()
                .id(userAccount.getId())
                .login(userAccount.getLogin())
                .email(userAccount.getEmail())
                .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                .userRole(userAccount.getUserData().getUserRole())
                .firstName(userAccount.getUserData().getFirstName())
                .lastName(userAccount.getUserData().getLastName())
                .build();
    }
}