package com.ordjoy.command;

public final class FrontCommandFactory {

    private FrontCommandFactory() {
        throw new UnsupportedOperationException();
    }

    public static FrontCommand getCommand(String commandName) {
        return FrontCommandType.valueOf(commandName.toUpperCase()).getFrontCommand();
    }
}