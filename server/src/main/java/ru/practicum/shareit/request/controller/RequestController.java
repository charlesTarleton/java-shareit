package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ReceivedRequestDto;
import ru.practicum.shareit.request.dto.ReturnRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private static final String CONTROLLER_LOG = "Контроллер запросов получил запрос на {}{}";
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ReturnRequestDto addRequest(@RequestBody ReceivedRequestDto requestDto,
                                       @RequestHeader(USER_HEADER) Long requestorId) {
        log.info(CONTROLLER_LOG, "добавление запроса: ", requestDto);
        return requestService.addRequest(requestDto, requestorId);
    }

    @GetMapping("/all")
    public List<ReturnRequestDto> getOthersRequests(
            @RequestParam(value = "from", required = false) Integer from,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение запросов постранично начиная с: ", from);
        return requestService.getOthersRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public ReturnRequestDto getRequest(@PathVariable Long requestId,
                                       @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение запроса с id: ", requestId);
        return requestService.getRequest(requestId, userId);
    }

    @GetMapping
    public List<ReturnRequestDto> getUserRequests(@RequestHeader(USER_HEADER) Long requestorId) {
        log.info(CONTROLLER_LOG, "получение всех запросов пользователя: ", requestorId);
        return requestService.getUserRequests(requestorId);
    }
}
