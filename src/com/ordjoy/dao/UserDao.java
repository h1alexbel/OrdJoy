package com.ordjoy.dao;

import com.ordjoy.filter.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserAccountData;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<Long, UserAccount> {

    UserAccount saveUserWithAccountData(UserAccount userAccount, UserAccountData userAccountData);

    void updateData(UserAccountData userAccountData);

    Optional<UserAccountData> findUserDataByUserId(Long userId);

    Optional<UserAccount> findUserByEmail(String email);

    List<UserAccount> findAll(UserAccountFilter filter);
}