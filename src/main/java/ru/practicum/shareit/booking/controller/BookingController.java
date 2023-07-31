package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.bookingUtils.BookingState;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

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
    public ReturnBookingDto addBooking(@RequestBody ReceivedBookingDto bookingDto,
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
    public ReturnBookingDto getBooking(@PathVariable Long bookingId) {
        log.info(LOG_MESSAGE, "получение брони с id: ", bookingId);
        return bookingService.getBooking(bookingId);
    }

    @GetMapping("/owner")
    public List<ReturnBookingDto> getOwnerBookings(
            @RequestParam(value = "state", defaultValue = "ALL") BookingState state, // бронь чужих вещей юзером
            @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(LOG_MESSAGE, "получение брони вещей, арендованных пользователем с id: ", ownerId);
        return bookingService.getOwnerBookings(state, ownerId);
    }


    @GetMapping
    public List<ReturnBookingDto> getOwnerItemsBookings(@RequestParam("state") BookingState state, // бронь вещей юзера
                                                        @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(LOG_MESSAGE, "получение брони вещей, принадлежащих пользователю с id: ", ownerId);
        return bookingService.getOwnerItemsBookings(state, ownerId);
    }
}
