package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.bookingUtils.BookingStatus;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.user.dto.UserDtoBooking;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReturnBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private UserDtoBooking booker;
    private ItemBookingDto item;
}
