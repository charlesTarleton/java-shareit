package ru.practicum.shareit.exceptions;

public class ItemWithoutOwnerException extends RuntimeException {
    public ItemWithoutOwnerException(String message) {
        super(message);
    }
}
