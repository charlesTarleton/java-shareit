package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.utils.BookingStatus;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Booking {
    private Long bookingId;
    private final LocalDate start;
    private final LocalDate end;
    private final Long itemId;
    private final Long bookerId;
    private BookingStatus bookingStatus;
}
