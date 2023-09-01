package ru.practicum.shareit.booking.controller;

import ru.practicum.shareit.booking.client.BookingClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.GatewayBookingDto;
import ru.practicum.shareit.booking.utils.GatewayBookingState;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class GatewayBookingController {
    private final BookingClient bookingClient;
    private static final String CONTROLLER_LOG = "Контроллер бронирования получил запрос на {}{}";
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addBooking(@Valid @RequestBody GatewayBookingDto bookingDto,
                                             @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "добавление брони: ", bookingDto);
        return bookingClient.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> setBookingStatus(@PathVariable Long bookingId,
                                                   @RequestParam(value = "approved",
                                                           defaultValue = "false") Boolean status,
                                                   @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(CONTROLLER_LOG, "изменение статуса одобрения владельцем брони с id: ", bookingId);
        return bookingClient.setBookingStatus(bookingId, status, ownerId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@PathVariable Long bookingId,
                                             @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение брони с id: ", bookingId);
        return bookingClient.getBooking(bookingId, userId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getOwnerBookings(
            @RequestParam(value = "state", defaultValue = "ALL") String stateText,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(CONTROLLER_LOG, "получение брони вещей, принадлежащих пользователем с id: ", ownerId);
        return bookingClient.getOwnerBookings(GatewayBookingState.from(stateText), from, size, ownerId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookerBookings(
            @RequestParam(value = "state", defaultValue = "ALL") String stateText,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestHeader(USER_HEADER) Long bookerId) {
        log.info(CONTROLLER_LOG, "получение брони вещей, арендованных пользователю с id: ", bookerId);
        return bookingClient.getBookerBookings(GatewayBookingState.from(stateText), from, size, bookerId);
    }
}