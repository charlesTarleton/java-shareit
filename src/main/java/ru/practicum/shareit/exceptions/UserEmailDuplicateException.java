package ru.practicum.shareit.exceptions;

public class UserEmailDuplicateException extends RuntimeException {
    public UserEmailDuplicateException(String message) {
        super(message);
    }
}
