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

    /**
     * @return instance of {@link UserService}
     */
    public static UserService getInstance() {
        return INSTANCE;
    }

    /**
     * Finds all Users with limit and offset
     *
     * @param filter sets limit, offset
     * @return List of {@link UserAccountDto} that represents user in database
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Gets all records in database where role = CLIENT_ROLE
     *
     * @return Long value that represents table records in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getUserRoleRecords() throws ServiceException {
        try {
            return userDao.getUserRoleTableRecords();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Gets all records in database where role = ADMIN_ROLE
     *
     * @return Long value that represents table records in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getAdminRoleRecords() throws ServiceException {
        try {
            return userDao.getAdminRoleTableRecords();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Saves new User with client role in database
     *
     * @param userAccount - entity to be saved in database
     * @return {@link UserAccountDto} saved user without password and payment data
     * @throws ServiceException if Dao layer can not execute method
     */
    public UserAccountDto saveNewUser(UserAccount userAccount) throws ServiceException {
        try {
            UserAccount savedUser = userDao.save(userAccount);
            return userAccountMapper.mapFrom(savedUser);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds User in database with relevant login & password
     *
     * @param login    user's login
     * @param password user's password
     * @return {@link Optional} of {@link UserAccountDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Check User by relevant login exists or not in database
     *
     * @param login User's login in database
     * @return boolean value {@code true} if {@link UserAccount}
     * by this login exists in database or {@code false} if not exists
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean isUserAccountExists(String login) throws ServiceException {
        boolean result = false;
        try {
            Optional<UserAccount> maybeUserByLogin = userDao.findUserByLogin(login);
            if (maybeUserByLogin.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Check User by relevant email exists or not in database
     *
     * @param email User's email in database
     * @return boolean value {@code true} if {@link UserAccount}
     * by this email exists in database or {@code false} if not exists
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Check login exists or not in database
     *
     * @param login User's login in database
     * @return boolean value {@code true} if login exists in database or {@code false} if not exists
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Find {@link UserAccountDto} by {@link UserAccount} id and return {@link Optional}
     * value of it or empty {@link Optional}
     *
     * @param id {@link UserAccount} id by which it will be found
     * @return {@link Optional} value of {@link UserAccountDto} if present in or empty {@link Optional}
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Updates {@link UserAccount} in database
     *
     * @param userAccount new value of {@link UserAccount}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateUserData(UserAccount userAccount) throws ServiceException {
        try {
            userDao.update(userAccount);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Transaction delete User by its id
     *
     * @param id User's id in database
     * @return boolean value {@code true} if successfully deleted of {@code false} if failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteUserById(Long id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Update discount percentage level by {@link UserAccount} email
     *
     * @param discountPercentageLevel new value of discount level
     * @param userEmail               User's email by which it will be found in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public void addDiscountPercentageLevel(Integer discountPercentageLevel, String userEmail) throws ServiceException {
        try {
            userDao.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds {@link UserAccount} discount level by user's id or {@link Optional} empty
     * if relevant user does not exist
     *
     * @param userId {@link UserAccount} id in database
     * @return {@link Optional} value of {@link Integer} that represents discount level
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<Integer> findDiscountPercentageLevelByUserId(Long userId) throws ServiceException {
        try {
            return userDao.findDiscountPercentageLevelByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds {@link UserAccount} discount level by user's email or {@link Optional} empty if
     * relevant user does not exist
     *
     * @param email {@link UserAccount} email in database
     * @return {@link Optional} value of {@link Integer} that represents discount level
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<Integer> findDiscountPercentageLevelByEmail(String email) throws ServiceException {
        Optional<Integer> maybeDiscountPercentageLevel;
        try {
            maybeDiscountPercentageLevel = userDao.findDiscountPercentageLevelByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeDiscountPercentageLevel;
    }

    /**
     * Find {@link UserAccountDto} by {@link UserAccount} login and return {@link Optional}
     * value of it or empty {@link Optional}
     *
     * @param login {@link UserAccount} login by which it will be found
     * @return {@link Optional} value of {@link UserAccountDto} if present in or empty {@link Optional}
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Find {@link UserAccountDto} by {@link UserAccount} email and return {@link Optional}
     * value of it or empty {@link Optional}
     *
     * @param email {@link UserAccount} email by which it will be found
     * @return {@link Optional} value of {@link UserAccountDto} if present in or empty {@link Optional}
     * @throws ServiceException if Dao layer can not execute method
     */
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

    /**
     * Make user account with client role from data
     *
     * @param email      email
     * @param login      login
     * @param password   password
     * @param firstName  firstName
     * @param lastName   lastName
     * @param ageToParse age that be parsed
     * @param cardNumber cardNumber
     * @return {@link UserAccount} with clien role
     */
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

    /**
     * Make {@link UserAccount} from {@link UserAccountDto} from session
     *
     * @param userAccountDto session User
     * @return {@link UserAccount} from session
     * @see javax.servlet.http.HttpSession
     */
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

    /**
     * Make admin account with admin role from data
     *
     * @param email      email
     * @param login      login
     * @param password   password
     * @param firstName  firstName
     * @param lastName   lastName
     * @param ageToParse age that be parsed
     * @param cardNumber cardNumber
     * @return {@link UserAccount} with admin role
     */
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