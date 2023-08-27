package ru.practicum.shareit.exceptions;

public class GatewayIllegalBookingStateException extends IllegalStateException {
    public GatewayIllegalBookingStateException(String message) {
        super(message);
    }
}
