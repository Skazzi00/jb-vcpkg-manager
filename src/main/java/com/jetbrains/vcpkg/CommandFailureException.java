package com.jetbrains.vcpkg;

public class CommandFailureException extends Exception {
    public CommandFailureException() {
    }

    public CommandFailureException(String message) {
        super(message);
    }

    public CommandFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandFailureException(Throwable cause) {
        super(cause);
    }
}
