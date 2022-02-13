package com.ordjoy.service;

import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    public void saveNewUserCompareByEmail() {
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
    public void saveNewUserCompareByLogin() {
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
    public void findUserByLoginAndPasswordNullCase() {
        UserService userService = UserService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword("test", null)),
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword(null, null)),
                () -> assertDoesNotThrow(() -> userService.findUserByLoginAndPassword(null, "pass"))
        );
    }

    @Test
    public void findUserByIdNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserById(null));
    }

    @Test
    public void updateUserData() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.updateUserData(null));
    }

    @Test
    public void deleteUserByIdNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.deleteUserById(null));
    }

    @Test
    public void addDiscountPercentageLevelNullCase() {
        UserService userService = UserService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        userService.addDiscountPercentageLevel(null, "test@gmail.com")),
                () -> assertDoesNotThrow(() ->
                        userService.addDiscountPercentageLevel(10, null))
        );
    }

    @Test
    public void findDiscountPercentageLevelByUserIdNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findDiscountPercentageLevelByUserId(null));
    }

    @Test
    public void findDiscountPercentageLevelByEmailNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findDiscountPercentageLevelByEmail(null));
    }

    @Test
    public void findUserByLoginNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserByLogin(null));
    }

    @Test
    public void findUserByEmailNullCase() {
        UserService userService = UserService.getInstance();
        assertDoesNotThrow(() -> userService.findUserByEmail(null));
    }
}