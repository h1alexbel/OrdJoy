package com.ordjoy.service;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dao.impl.UserDaoImpl;
import com.ordjoy.dto.UserDto;
import com.ordjoy.entity.UserAccount;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class UserService {

    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserService INSTANCE = new UserService();

    private UserService() {

    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<UserDto> findAllUsersWithLimitOffset(UserAccountFilter filter) {
        return userDao.findAll(filter).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                ))
                .collect(toList());
    }

    public UserAccount saveUser(UserAccount userAccount) {
        return userDao.save(userAccount);
    }

    public Optional<UserDto> findUserById(Long id) {
        return userDao.findById(id).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }

    public void updateUserData(UserAccount userAccount) {
        userDao.update(userAccount);
    }

    public boolean deleteUserById(Long id) {
        return userDao.deleteById(id);
    }

    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) {
        userDao.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
    }

    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) {
        return userDao.findDiscountPercentageLevelByUserId(userId);
    }

    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) {
        return userDao.findDiscountPercentageLevelByEmail(email);
    }

    public Optional<UserDto> findUserByLogin(String login) {
        return userDao.findUserByLogin(login).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return userDao.findUserByEmail(email).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }
}