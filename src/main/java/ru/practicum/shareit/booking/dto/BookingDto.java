package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.utils.BookingStatus;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long bookingId;
    private LocalDate start;
    private LocalDate end;
    private Long itemId;
    private Long bookerId;
    private BookingStatus bookingStatus;
}
