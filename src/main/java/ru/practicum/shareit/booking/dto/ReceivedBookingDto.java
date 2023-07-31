package ru.practicum.shareit.booking.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ReceivedBookingDto {
    private Long itemId;
    @NotBlank
    private LocalDateTime start;
    @NotBlank
    private LocalDateTime end;
}
