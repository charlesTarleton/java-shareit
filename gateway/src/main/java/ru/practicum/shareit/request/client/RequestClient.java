package ru.practicum.shareit.request.client;

import ru.practicum.shareit.client.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.request.dto.GatewayRequestDto;

import java.util.Map;

@Service
@Slf4j
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";
    private static final String CLIENT_LOG = "Клиент запросов получил запрос на {}{}";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addRequest(GatewayRequestDto requestDto, Long requestorId) {
        log.info(CLIENT_LOG, "добавление запроса: ", requestDto);
        return post("", requestorId, requestDto);
    }

    public ResponseEntity<Object> getOthersRequests(Integer from, Integer size, Long userId) {
        log.info(CLIENT_LOG, "получение запросов постранично начиная с: ", from);
        return get("/all", userId, Map.of("from", from, "size", size));
    }

    public ResponseEntity<Object> getRequest(Long requestId, Long userId) {
        log.info(CLIENT_LOG, "получение запроса с id: ", requestId);
        return get("/" + requestId, userId);
    }

    public ResponseEntity<Object> getUserRequests(Long requestorId) {
        log.info(CLIENT_LOG, "получение всех запросов пользователя: ", requestorId);
        return get("", requestorId);
    }
}