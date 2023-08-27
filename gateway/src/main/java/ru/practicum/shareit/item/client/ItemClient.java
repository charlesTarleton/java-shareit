package ru.practicum.shareit.item.client;

import ru.practicum.shareit.client.BaseClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.item.dto.GatewayCommentDto;
import ru.practicum.shareit.item.dto.GatewayItemDto;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";
    private static final String CLIENT_LOG = "Клиент предметов получил запрос на {}{}";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(GatewayItemDto itemDto, Long ownerId) {
        log.info(CLIENT_LOG, "добавление предмета: ", itemDto);
        return post("", ownerId, itemDto);
    }

    public ResponseEntity<Object> updateItem(Long itemId, GatewayItemDto itemDto, Long ownerId) {
        log.info(CLIENT_LOG, "обновление предмета с id: ", itemId);
        return patch("/" + itemDto, ownerId, itemDto);
    }

    public void deleteItem(Long itemId, Long ownerId) {
        log.info(CLIENT_LOG, "удаление предмета с id: ", itemId);
        delete("/" + itemId, ownerId);
    }

    public ResponseEntity<Object> getItem(Long itemId, Long userId) {
        log.info(CLIENT_LOG, "получение предмета с id: ", itemId);
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getItemsByName(String text, Integer from, Integer size) {
        log.info(CLIENT_LOG, "получение всех предметов, содержащих в названии: ", text);
        if (text.isBlank()) {
            return ResponseEntity.ok().body(List.of());
        }
        return get("/search?from="+ from + "&size=" + size + "&text=" + text, null, Map.of(
                "from", from,
                "size", size,
                "text", text));
    }

    public ResponseEntity<Object> getItemsByOwner(Long ownerId, Integer from, Integer size) {
        log.info(CLIENT_LOG, "получение всех предметов пользователя с id: ", ownerId);
        return get("?from={from}&size={size}", ownerId, Map.of("from", from, "size", size));
    }

    public ResponseEntity<Object> addCommentToItem(Long itemId, GatewayCommentDto commentDto, Long authorId) {
        log.info(CLIENT_LOG, "добавление комментария предмету с id: ", itemId);
        return post("/" + itemId + "/comment", authorId, commentDto);
    }
}