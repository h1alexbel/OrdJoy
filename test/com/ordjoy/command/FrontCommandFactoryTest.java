package com.ordjoy.command;

import com.ordjoy.command.impl.LoginCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FrontCommandFactoryTest {

    @Test
    public void nullCommandCase() {
        assertDoesNotThrow(() -> FrontCommandFactory.getCommand(null));
    }

    @Test
    public void commandDefine() {
        String commandName = "login";
        FrontCommand actual = FrontCommandFactory.getCommand(commandName);
        assertTrue(actual instanceof LoginCommand);
    }
}