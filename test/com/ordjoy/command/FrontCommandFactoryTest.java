package com.ordjoy.command;

import com.ordjoy.command.impl.LoginCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class FrontCommandFactoryTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("commandName null case")
    void nullCommandCase(String name) {
        assertDoesNotThrow(() -> FrontCommandFactory.getCommand(name));
    }

    @Test
    @DisplayName("Login command define test")
    void commandDefine() {
        String commandName = "login";
        FrontCommand actual = FrontCommandFactory.getCommand(commandName);
        assertTrue(actual instanceof LoginCommand);
    }
}