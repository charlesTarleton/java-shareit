package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.bookingUtils.BookingState;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.IllegalBookingStateException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private static final String LOG_MESSAGE = "Контроллер бронирования получил запрос на {}{}";
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ReturnBookingDto addBooking(@Valid @RequestBody ReceivedBookingDto bookingDto,
                                       @RequestHeader(USER_HEADER) Long userId) {
        log.info(LOG_MESSAGE, "добавление брони: ", bookingDto);
        return bookingService.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ReturnBookingDto setBookingStatus(@PathVariable Long bookingId, @RequestParam("approved") Boolean status,
                                             @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(LOG_MESSAGE, "изменение статуса одобрения владельцем брони с id: ", bookingId);
        return bookingService.setBookingStatus(bookingId, status, ownerId);
    }

    @GetMapping("/{bookingId}")
    public ReturnBookingDto getBooking(@PathVariable Long bookingId, @RequestHeader(USER_HEADER) Long userId) {
        log.info(LOG_MESSAGE, "получение брони с id: ", bookingId);
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping("/owner")
    public List<ReturnBookingDto> getOwnerBookings(
            @RequestParam(value = "state", required = false) String stateText,
            @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(LOG_MESSAGE, "получение брони вещей, принадлежащих пользователем с id: ", ownerId);
        return bookingService.getOwnerBookings(getBookingsStateFromString(stateText), ownerId);
    }

    @GetMapping
    public List<ReturnBookingDto> getBookerBookings(
            @RequestParam(value = "state", required = false) String stateText,
            @RequestHeader(USER_HEADER) Long bookerId) {
        log.info(LOG_MESSAGE, "получение брони вещей, арендованных пользователю с id: ", bookerId);
        return bookingService.getBookerBookings(getBookingsStateFromString(stateText), bookerId);
    }

    private BookingState getBookingsStateFromString(String stateText) {
        try {
            if (stateText == null) {
                return BookingState.ALL;
            } else {
                return BookingState.valueOf(stateText);
            }
        } catch (MethodArgumentTypeMismatchException e) {
            throw new IllegalBookingStateException("Ошибка. в BookingState было передано недопустимое значение");
        }
    }
}
