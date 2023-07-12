package ru.practicum.shareit.booking;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryBookingRepository {
    private final Set<Booking> bookingsList = new HashSet<>();
}
