package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private Long itemRequestId;
    private String description;
    private Long requestorId; // соответствует userId
    private LocalDate created;
}
