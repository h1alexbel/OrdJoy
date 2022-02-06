package com.ordjoy.service;

import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dao.impl.UserDaoImpl;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.UserAccountMapper;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class UserService {

    private static final Integer STARTER_DISCOUNT_PERCENTAGE_LEVEL = 0;
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    private static final UserService INSTANCE = new UserService();
    private final UserAccountMapper userAccountMapper = UserAccountMapper.getInstance();

    private UserService() {

    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<UserAccountDto> findAllUsersWithLimitOffset(UserAccountFilter filter) throws ServiceException {
        List<UserAccountDto> users;
        try {
            users = userDao.findAll(filter).stream()
                    .map(userAccount -> UserAccountDto.builder()
                            .id(userAccount.getId())
                            .login(userAccount.getLogin())
                            .email(userAccount.getEmail())
                            .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                            .userRole(userAccount.getUserData().getUserRole())
                            .firstName(userAccount.getUserData().getFirstName())
                            .lastName(userAccount.getUserData().getLastName())
                            .build())
                    .collect(toList());
            return users;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public UserAccountDto saveNewUser(UserAccount userAccount) throws ServiceException {
        try {
            UserAccount savedUser = userDao.save(userAccount);
            return userAccountMapper.mapFrom(savedUser);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<UserAccountDto> findUserByLoginAndPassword(String login, String password) throws ServiceException {
        Optional<UserAccountDto> maybeUser;
        try {
            maybeUser = userDao.findUserAccountByLoginAndPassword(login, password).stream()
                    .map(userAccount -> UserAccountDto.builder()
                            .id(userAccount.getId())
                            .login(userAccount.getLogin())
                            .email(userAccount.getEmail())
                            .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                            .userRole(userAccount.getUserData().getUserRole())
                            .firstName(userAccount.getUserData().getFirstName())
                            .lastName(userAccount.getUserData().getLastName())
                            .build())
                    .findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeUser;
    }

    public boolean isUserAccountExists(String login, Long id) throws ServiceException {
        boolean result = false;
        try {
            Optional<UserAccount> maybeUserByLogin = userDao.findUserByLogin(login);
            Optional<UserAccount> maybeUserById = userDao.findById(id);
            if (maybeUserByLogin.isPresent() && maybeUserById.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public boolean isEmailExists(String email) throws ServiceException {
        boolean result = false;
        try {
            Optional<UserAccount> maybeUser = userDao.findUserByEmail(email);
            if (maybeUser.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public boolean isLoginExists(String login) throws ServiceException {
        boolean result = false;
        try {
            Optional<UserAccount> maybeUser = userDao.findUserByLogin(login);
            if (maybeUser.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public Optional<UserAccountDto> findUserById(Long id) throws ServiceException {
        Optional<UserAccountDto> maybeUser;
        try {
            maybeUser = userDao.findById(id).stream()
                    .map(userAccount -> UserAccountDto.builder()
                            .id(userAccount.getId())
                            .login(userAccount.getLogin())
                            .email(userAccount.getEmail())
                            .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                            .userRole(userAccount.getUserData().getUserRole())
                            .firstName(userAccount.getUserData().getFirstName())
                            .lastName(userAccount.getUserData().getLastName())
                            .build())
                    .findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeUser;
    }

    public void updateUserData(UserAccount userAccount) throws ServiceException {
        try {
            userDao.update(userAccount);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteUserById(Long id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws ServiceException {
        try {
            userDao.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws ServiceException {
        try {
            return userDao.findDiscountPercentageLevelByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws ServiceException {
        Optional<Integer> maybeDiscountPercentageLevel;
        try {
            maybeDiscountPercentageLevel = userDao.findDiscountPercentageLevelByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeDiscountPercentageLevel;
    }

    public Optional<UserAccountDto> findUserByLogin(String login) throws ServiceException {
        Optional<UserAccountDto> maybeUser;
        try {
            maybeUser = userDao.findUserByLogin(login).stream()
                    .map(userAccount -> UserAccountDto.builder()
                            .id(userAccount.getId())
                            .login(userAccount.getLogin())
                            .email(userAccount.getEmail())
                            .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                            .userRole(userAccount.getUserData().getUserRole())
                            .firstName(userAccount.getUserData().getFirstName())
                            .lastName(userAccount.getUserData().getLastName())
                            .build())
                    .findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeUser;
    }

    public Optional<UserAccountDto> findUserByEmail(String email) throws ServiceException {
        Optional<UserAccountDto> maybeUser;
        try {
            maybeUser = userDao.findUserByEmail(email).stream()
                    .map(userAccount -> UserAccountDto.builder()
                            .id(userAccount.getId())
                            .login(userAccount.getLogin())
                            .email(userAccount.getEmail())
                            .discountPercentageLevel(userAccount.getDiscountPercentageLevel())
                            .userRole(userAccount.getUserData().getUserRole())
                            .firstName(userAccount.getUserData().getFirstName())
                            .lastName(userAccount.getUserData().getLastName())
                            .build())
                    .findFirst();
            return maybeUser;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public UserAccount buildUser(
            String email,
            String login,
            String password,
            String firstName,
            String lastName,
            String ageToParse,
            String cardNumber) {
        UserData data = UserData.builder()
                .userRole(UserRole.CLIENT_ROLE)
                .firstName(firstName)
                .lastName(lastName)
                .age(Integer.parseInt(ageToParse))
                .cardNumber(cardNumber)
                .build();
        return UserAccount.builder()
                .email(email)
                .login(login)
                .password(password)
                .discountPercentageLevel(STARTER_DISCOUNT_PERCENTAGE_LEVEL)
                .userData(data)
                .build();
    }

    public UserAccount buildUserAccountFromSession(UserAccountDto userAccountDto) {
        UserData data = UserData.builder()
                .userRole(userAccountDto.getRole())
                .firstName(userAccountDto.getFirstName())
                .lastName(userAccountDto.getLastName())
                .build();
        return UserAccount.builder()
                .email(userAccountDto.getEmail())
                .login(userAccountDto.getLogin())
                .discountPercentageLevel(userAccountDto.getDiscountPercentageLevel())
                .userData(data)
                .build();
    }

    public UserAccount buildAdmin(
            String email,
            String login,
            String password,
            String firstName,
            String lastName,
            String ageToParse,
            String cardNumber) {
        UserData data = UserData.builder()
                .userRole(UserRole.ADMIN_ROLE)
                .firstName(firstName)
                .lastName(lastName)
                .age(Integer.parseInt(ageToParse))
                .cardNumber(cardNumber)
                .build();
        return UserAccount.builder()
                .email(email)
                .login(login)
                .password(password)
                .discountPercentageLevel(STARTER_DISCOUNT_PERCENTAGE_LEVEL)
                .userData(data)
                .build();
    }
}