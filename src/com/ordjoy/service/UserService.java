package com.ordjoy.service;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dao.impl.UserDaoImpl;
import com.ordjoy.dto.UserDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ServiceException;

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

    public List<UserDto> findAllUsersWithLimitOffset(UserAccountFilter filter) throws ServiceException {
        return userDao.findAll(filter).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                ))
                .collect(toList());
    }

    public UserAccount saveUser(UserAccount userAccount) throws ServiceException {
        return userDao.save(userAccount);
    }

    public Optional<UserDto> findUserById(Long id) throws ServiceException {
        return userDao.findById(id).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }

    public void updateUserData(UserAccount userAccount) throws ServiceException {
        userDao.update(userAccount);
    }

    public boolean deleteUserById(Long id) throws ServiceException {
        return userDao.deleteById(id);
    }

    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws ServiceException {
        userDao.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
    }

    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws ServiceException {
        return userDao.findDiscountPercentageLevelByUserId(userId);
    }

    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws ServiceException {
        return userDao.findDiscountPercentageLevelByEmail(email);
    }

    public Optional<UserDto> findUserByLogin(String login) throws ServiceException {
        return userDao.findUserByLogin(login).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }

    public Optional<UserDto> findUserByEmail(String email) throws ServiceException {
        return userDao.findUserByEmail(email).stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getPassword()
                )).findFirst();
    }
}