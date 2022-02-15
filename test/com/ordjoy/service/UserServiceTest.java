package com.ordjoy.service;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    @DisplayName("save user and compare by email test")
    void saveNewUserCompareByEmail() {
        UserService userService = UserService.getInstance();
        UserAccount user = UserAccount.builder()
                .email("test@gmail.com")
                .login("test")
                .password("pass")
                .userData(UserData.builder()
                        .cardNumber("12132515891")
                        .age(19)
                        .firstName("Test")
                        .lastName("Testing")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .discountPercentageLevel(0)
                .build();
        try {
            UserAccountDto saved = userService.saveNewUser(user);
            UserAccountDto expectedToCompare = UserAccountDto.builder()
                    .email("test@gmail.com")
                    .login("test")
                    .firstName("Test")
                    .lastName("Testing")
                    .userRole(UserRole.CLIENT_ROLE)
                    .discountPercentageLevel(0)
                    .build();
            assertEquals(expectedToCompare.getEmail(), saved.getEmail(), "Users email must be equal!");
            assertEquals(expectedToCompare.getLogin(), saved.getLogin(), "Users login must be equal!");
            assertEquals(expectedToCompare.getFirstName(), saved.getFirstName(),
                    "Users first names must be equal!");
            assertEquals(expectedToCompare.getLastName(), saved.getLastName(),
                    "Users last names must be equal!");
            assertEquals(expectedToCompare.getDiscountPercentageLevel(), saved.getDiscountPercentageLevel(),
                    "Users discount percentage levels must be equal!");
            assertEquals(expectedToCompare.getRole(), saved.getRole(), "Users roles must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("save user and compare by login test")
    void saveNewUserCompareByLogin() {
        UserService userService = UserService.getInstance();
        UserAccount user = UserAccount.builder()
                .email("test@gmail.com")
                .login("test")
                .password("pass")
                .userData(UserData.builder()
                        .cardNumber("12132515891")
                        .age(19)
                        .firstName("Test")
                        .lastName("Testing")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .discountPercentageLevel(0)
                .build();
        try {
            UserAccountDto saved = userService.saveNewUser(user);
            UserAccountDto expectedToCompare = UserAccountDto.builder().login("test").build();
            boolean actual = saved.getLogin().equals(expectedToCompare.getLogin());
            boolean expected = true;
            assertEquals(expected, actual);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findUserByLoginAndPasswordNullCase() {
        UserService userService = UserService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword("test", null)),
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword(null, null)),
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword(null, "pass"))
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find user by id null case test")
    void findUserByIdNullCase(Long id) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update user null case test")
    void updateUserData(UserAccount user) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.updateUserData(user));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete user null case test")
    void deleteUserByIdNullCase(Long id) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.deleteUserById(id));
    }

    @Test
    void addDiscountPercentageLevelNullCase() {
        UserService userService = UserService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        userService.addDiscountPercentageLevel(null, "test@gmail.com")),
                () -> assertDoesNotThrow(() ->
                        userService.addDiscountPercentageLevel(10, null))
        );
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find discount percentage level by id null case test")
    void findDiscountPercentageLevelByUserIdNullCase(Long id) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findDiscountPercentageLevelByUserId(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find discount percentage level by email null case test")
    void findDiscountPercentageLevelByEmailNullCase(String email) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findDiscountPercentageLevelByEmail(email));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find user by login null case test")
    void findUserByLoginNullCase(String login) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserByLogin(login));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find user by email case test")
    void findUserByEmailNullCase(String email) {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserByEmail(email));
    }
}