package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.bookingUtils.BookingStatus;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.user.dto.UserDtoBooking;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReturnBookingDto {
    private Long id;
    @NotBlank
    private LocalDateTime start;
    @NotBlank
    private LocalDateTime end;
    private BookingStatus status;
    @NotBlank
    private UserDtoBooking booker;
    @NotBlank
    private ItemBookingDto item;
}
