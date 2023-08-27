package ru.practicum.shareit.booking.bookingUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState getBookingsStateFromString(String stateText) {
        log.info("Начата процедура получения типа запроса броней: {}", stateText);
        return BookingState.valueOf(stateText);
    }
}
