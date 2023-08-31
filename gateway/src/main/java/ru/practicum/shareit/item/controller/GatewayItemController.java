package ru.practicum.shareit.item.controller;

import org.springframework.stereotype.Controller;
import ru.practicum.shareit.item.client.ItemClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.GatewayCommentDto;
import ru.practicum.shareit.item.dto.GatewayItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class GatewayItemController {
    private final ItemClient itemClient;
    private static final String CONTROLLER_LOG = "Контроллер предметов получил запрос на {}{}";
    private static final String USER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> addItem(@Valid @RequestBody GatewayItemDto itemDto,
                                          @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(CONTROLLER_LOG, "добавление предмета: ", itemDto);
        return itemClient.addItem(itemDto, ownerId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable("id") Long itemId,
                                             @RequestBody GatewayItemDto itemDto,
                                             @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(CONTROLLER_LOG, "обновление предмета с id: ", itemId);
        return itemClient.updateItem(itemId, itemDto, ownerId);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteItem(@PathVariable("id") Long itemId, @RequestHeader(USER_HEADER) Long ownerId) {
        log.info(CONTROLLER_LOG, "удаление предмета с id: ", itemId);
        itemClient.deleteItem(itemId, ownerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable("id") Long itemId, @RequestHeader(USER_HEADER) Long userId) {
        log.info(CONTROLLER_LOG, "получение предмета с id: ", itemId);
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsByName(
            @RequestParam("text") String text,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {
        log.info(CONTROLLER_LOG, "получение всех предметов, содержащих в названии: ", text);
        return itemClient.getItemsByName(text, from, size);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByOwner(
            @RequestHeader(USER_HEADER) Long ownerId,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "20") Integer size) {
        log.info(CONTROLLER_LOG, "получение всех предметов пользователя с id: ", ownerId);
        return itemClient.getItemsByOwner(ownerId, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addCommentToItem(@PathVariable Long itemId,
                                                   @Valid @RequestBody GatewayCommentDto commentDto,
                                                   @RequestHeader(USER_HEADER) Long authorId) {
        log.info(CONTROLLER_LOG, "добавление комментария предмету с id: ", itemId);
        return itemClient.addCommentToItem(itemId, commentDto, authorId);
    }
}
