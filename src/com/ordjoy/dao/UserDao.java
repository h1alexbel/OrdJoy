package com.ordjoy.dao;

import com.ordjoy.dto.UserAccountFilter;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserAccountData;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<UserAccount> {

    UserAccount saveUser(UserAccount userAccount);

    UserAccount saveUserWithAccountData(UserAccount userAccount, UserAccountData userAccountData);

    void updateData(UserAccountData userAccountData);

    Optional<UserAccountData> findUserDataByUserId(Long userId);

    Optional<UserAccount> findUserByEmail(String email);

    List<UserAccount> findAll(UserAccountFilter filter);
}