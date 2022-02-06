package com.ordjoy.command;

public final class FrontCommandFactory {

    private FrontCommandFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Defines Concrete Command by command name
     *
     * @param commandName String representation of command name
     * @return {@link FrontCommand} command from {@link FrontCommandType}
     * @see FrontCommandType
     */
    public static FrontCommand getCommand(String commandName) {
        return FrontCommandType.valueOf(commandName.toUpperCase()).getFrontCommand();
    }
}