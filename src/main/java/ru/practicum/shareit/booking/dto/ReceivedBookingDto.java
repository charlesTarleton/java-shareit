package ru.practicum.shareit.booking.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
public class ReceivedBookingDto {
    private Long itemId;
    @Future
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
}
