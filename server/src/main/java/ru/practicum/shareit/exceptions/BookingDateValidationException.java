package ru.practicum.shareit.exceptions;

public class BookingDateValidationException extends RuntimeException {
    public BookingDateValidationException(String message) {
        super(message);
    }
}
