package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.RequestClient;
import ru.practicum.shareit.request.dto.GatewayRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Slf4j
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class GatewayRequestController {
    private final RequestClient requestClient;
    private static final String CONTROLLER_LOG = "Контроллер запросов получил запрос на {}{}";
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addRequest(@Valid @RequestBody GatewayRequestDto requestDto,
                                             @RequestHeader(USER_HEADER) Long requestorId) {
        log.info(CONTROLLER_LOG, "добавление запроса: ", requestDto);
        return requestClient.addRequest(requestDto, requestorId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getOthersRequests(
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение запросов постранично начиная с: ", from);
        return requestClient.getOthersRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId,
                                             @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение запроса с id: ", requestId);
        return requestClient.getRequest(requestId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(USER_HEADER) Long requestorId) {
        log.info(CONTROLLER_LOG, "получение всех запросов пользователя: ", requestorId);
        return requestClient.getUserRequests(requestorId);
    }
}