package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingItemDto {
    private Long id;
    private Long itemId;
    private Long bookerId;
}
