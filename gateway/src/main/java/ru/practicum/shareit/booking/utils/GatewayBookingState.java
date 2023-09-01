package ru.practicum.shareit.booking.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.exceptions.GatewayIllegalBookingStateException;

@Slf4j
public enum GatewayBookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static GatewayBookingState from(String stateText) {
        log.info("Начата процедура получения типа запроса броней: {}", stateText);
        try {
            return GatewayBookingState.valueOf(stateText);
        } catch (MethodArgumentTypeMismatchException | IllegalArgumentException e) {
            throw new GatewayIllegalBookingStateException("Ошибка. в BookingState было передано недопустимое значение");
        }
    }
}