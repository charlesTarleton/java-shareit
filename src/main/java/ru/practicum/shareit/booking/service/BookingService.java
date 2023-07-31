package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.bookingUtils.BookingState;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

public interface BookingService {
    ReturnBookingDto addBooking(ReceivedBookingDto bookingDto, Long userId);

    ReturnBookingDto setBookingStatus(Long bookingId, Boolean status, Long ownerId);

    ReturnBookingDto getBooking(Long bookingId);

    List<ReturnBookingDto> getOwnerBookings(BookingState state, Long ownerId);

    List<ReturnBookingDto> getOwnerItemsBookings(BookingState state, Long ownerId);
}
