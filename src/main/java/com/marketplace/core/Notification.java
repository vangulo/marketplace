package com.marketplace.core;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
This provides a way to return multiple messeges when an exception occurs
provides a user more information with whats wrong with a request
 */
public class Notification {
    private List<Error> errors = new ArrayList<>();

    public void addError(String message) {
        addError(message, null);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String errorMessage() {
        return errors.stream()
                .map(e -> e.message)
                .collect(Collectors.joining(", "));
    }

    //might be uncessary
    public void addError(String message, Exception e) {
        errors.add(new Error(message, e));
    }

    private static class Error {
        String message;
        Exception cause;

        private Error(String message, Exception cause) {
            this.message = message;
            this.cause = cause;
        }
    }
}
