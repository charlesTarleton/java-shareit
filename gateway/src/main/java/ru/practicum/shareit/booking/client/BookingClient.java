package ru.practicum.shareit.booking.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.GatewayBookingDto;
import ru.practicum.shareit.booking.utils.GatewayBookingState;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
@Slf4j
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";
    private static final String CLIENT_LOG = "Клиент бронирований получил запрос на {}{}";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addBooking(GatewayBookingDto bookingDto, Long userId) {
        log.info(CLIENT_LOG, "добавление брони: ", bookingDto);
        return post("", userId, bookingDto);
    }


    public ResponseEntity<Object> setBookingStatus(Long bookingId, Boolean approved, Long userId) {
        log.info(CLIENT_LOG, "изменение статуса одобрения владельцем брони с id: ", bookingId);
        return patch("/" + bookingId + "?approved=" + approved, userId,
                Map.of("approved", approved), null);
    }

    public ResponseEntity<Object> getBooking(Long bookingId, Long userId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getBookerBookings(GatewayBookingState state, Integer from,
                                                    Integer size, Long bookerId) {
        return get("?from=" + from + "&size=" + size + "&state=" + state, bookerId, Map.of(
                "from", from,
                "size", size,
                "state", state
        ));
    }

    public ResponseEntity<Object> getOwnerBookings(GatewayBookingState state, Integer from,
                                                   Integer size, Long ownerId) {
        return get("/owner?from=" + from + "&size=" + size + "&state=" + state, ownerId, Map.of(
                "from", from,
                "size", size,
                "state", state
        ));
    }
}