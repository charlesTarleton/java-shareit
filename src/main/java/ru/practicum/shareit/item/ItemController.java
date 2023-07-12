package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private static final String LOG_MESSAGE = "Контроллер предметов получил запрос на ";

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Long owner) {
        log.info(LOG_MESSAGE + "добавление предмета: " + item);
        return itemService.addItem(item, owner);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable("id") Long itemId,
                              @RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Long owner) {
        log.info(LOG_MESSAGE + "обновление предмета: " + item + "; c id: " + itemId);
        return itemService.updateItem(itemId, item, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long itemId, @RequestHeader("X-Sharer-User-Id") Long owner) {
        log.info(LOG_MESSAGE + "удаление предмета с id: " + itemId);
        itemService.deleteItem(itemId, owner);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable("id") Long itemId) {
        log.info(LOG_MESSAGE + "получение предмета с id: " + itemId);
        return itemService.getItem(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsByName(@RequestParam("text") String name) {
        log.info(LOG_MESSAGE + "получение всех предметов, содержащих в названии: " + name);
        return itemService.getItemsByName(name);
    }

    @GetMapping
    public List<ItemDto> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") Long owner) {
        log.info(LOG_MESSAGE + "получение всех предметов пользователя с id: " + owner);
        return itemService.getItemsByOwner(owner);
    }


}
