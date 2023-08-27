package ru.practicum.shareit.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GatewayErrorResponse {
    private final String error;
    private final String description;
}
