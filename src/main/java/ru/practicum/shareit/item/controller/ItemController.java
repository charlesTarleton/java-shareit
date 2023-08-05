package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static final String LOG_MESSAGE = "Контроллер предметов получил запрос на {}, {}";
    private static final String OWNER_HEADER = "X-Sharer-User-Id";

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody ItemDto itemDto, @RequestHeader(OWNER_HEADER) Long owner) {
        log.info(LOG_MESSAGE, "добавление предмета: ", itemDto);
        return itemService.addItem(itemDto, owner);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable("id") Long itemId,
                              @RequestBody ItemDto itemDto, @RequestHeader(OWNER_HEADER) Long owner) {
        log.info(LOG_MESSAGE, "обновление предмета с id: ", itemId);
        return itemService.updateItem(itemId, itemDto, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long itemId, @RequestHeader(OWNER_HEADER) Long owner) {
        log.info(LOG_MESSAGE, "удаление предмета с id: ", itemId);
        itemService.deleteItem(itemId, owner);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable("id") Long itemId) {
        log.info(LOG_MESSAGE, "получение предмета с id: ", itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByName(@RequestParam("text") String name) {
        log.info(LOG_MESSAGE, "получение всех предметов, содержащих в названии: ", name);
        return itemService.getItemsByName(name);
    }

    @GetMapping
    public List<ItemDto> getItemsByOwner(@RequestHeader(OWNER_HEADER) Long owner) {
        log.info(LOG_MESSAGE, "получение всех предметов пользователя с id: ", owner);
        return itemService.getItemsByOwner(owner);
    }
}
