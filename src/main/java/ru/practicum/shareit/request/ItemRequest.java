package ru.practicum.shareit.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ItemRequest {
    private final Long itemRequestId;
    private final String description;
    private final Long requestorId; // соответствует userId
    private final LocalDate created;
}
